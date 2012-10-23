/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../security/security-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class SecurityServiceTest {

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient AuthenticationUserDetailsService userDetailsService;

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void anonymousUser() {
		final SspUser anon = securityService.anonymousUser();
		assertNotNull("Should return Anon User", anon);
		assertEquals("Anon user username incorrect",
				SspUser.ANONYMOUS_PERSON_USERNAME, anon.getUsername());
	}

	@Test
	public void noAuthAdminUser() {
		final SspUser noAuthAdmin = securityService.noAuthAdminUser();
		assertNotNull("Should return Admin user", noAuthAdmin);
		assertEquals("Admin user id incorrect", Person.SYSTEM_ADMINISTRATOR_ID,
				noAuthAdmin.getPerson()
						.getId());
	}

	@Test
	public void currentUserKen() {
		authenticateWithUsername("ken", "ROLE_SERVICE_READ");

		final SspUser user = securityService.currentUser();

		assertEquals("username should be ken", user.getUsername(), "ken");

	}

	@Test
	public void currentUserAnon() {
		authenticateWithPrincipal(SspUser.ANONYMOUS_PERSON_USERNAME,
				"ROLE_SERVICE_READ");

		final SspUser user = securityService.currentUser();

		assertEquals("username should be anonymous", user.getUsername(),
				SspUser.ANONYMOUS_PERSON_USERNAME);

	}

	@Test
	public void currentUserNull() {
		assertNull("If not authenticated, should return null",
				securityService.currentUser());
	}

	@Test
	public void currentlyAuthenticatedUserKen() {
		authenticateWithUsername("ken", "ROLE_SERVICE_READ");

		final SspUser user = securityService.currentlyAuthenticatedUser();

		assertEquals("username should be ken", user.getUsername(), "ken");
	}

	@Test
	public void currentlyAuthenticatedUserAnon() {
		authenticateWithPrincipal(SspUser.ANONYMOUS_PERSON_USERNAME,
				"ROLE_SERVICE_READ");
		assertNull("If not authenticated, should return null",
				securityService.currentlyAuthenticatedUser());
	}

	@Test
	public void currentlyAuthenticatedUserNull() {
		assertNull("If not authenticated, should return null",
				securityService.currentlyAuthenticatedUser());
	}

	@Test
	public void isAuthenticated() {
		authenticateWithUsername("username", "ROLE_SERVICE_READ");
	}

	@Test
	public void hasAuthority() {
		authenticateWithUsername("username", "ROLE_SERVICE_READ");
		assertTrue("Should have role that we just passed to it",
				securityService.hasAuthority("ROLE_SERVICE_READ"));
	}

	private void authenticateWithUsername(final String username,
			final String... permissions) {
		final SspUser preAuthUser = new SspUser(username, "password",
				true, true, true, true, getAuthList(permissions));
		authenticateWithPrincipal(preAuthUser, permissions);
	}

	private void authenticateWithPrincipal(final Object principal,
			final String... permissions) {

		assertNotNull("Security Service was not injected properly",
				securityService);
		assertNotNull("User details Service was not injected properly",
				userDetailsService);

		assertFalse("We should not be authenticated yet",
				securityService.isAuthenticated());

		final Authentication auth = new UsernamePasswordAuthenticationToken(
				principal, "password", getAuthList(permissions));

		SecurityContextHolder.getContext()
				.setAuthentication(auth);

		assertTrue("We should be authenticated now", SecurityContextHolder
				.getContext().getAuthentication()
				.isAuthenticated());
	}

	private List<GrantedAuthority> getAuthList(final String... permissions) {
		final List<GrantedAuthority> authorities = Lists.newArrayList();
		for (String permission : permissions) {
			authorities.add(new GrantedAuthorityImpl(permission));
		}
		return authorities;
	}

}
