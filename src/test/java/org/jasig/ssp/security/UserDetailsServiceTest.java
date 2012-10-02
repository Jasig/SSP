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
package org.jasig.ssp.security; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.jasig.ssp.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("security-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class UserDetailsServiceTest {

	@Autowired
	private transient SspUserDetailsService userDetailsService;

	@Autowired
	private transient PersonDao personDao;

	@Autowired
	private transient PersonService personService;

	private static final String TEST_USERNAME = "adaLovelace";

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void loadUserDetailsForKen() {
		final Authentication token = new UsernamePasswordAuthenticationToken(
				"ken", "password");
		final SspUser user = (SspUser) userDetailsService
				.loadUserDetails(token);
		assertNotNull("User should not have been null.", user);
		assertEquals("Usernames did not match.", "ken", user.getUsername());
	}

	@Test
	public void loadUserDetailsForNewUser() throws ObjectNotFoundException {
		final PersonAttributesResult adaAttribs = new PersonAttributesResult(
				"adaLovelace", "Ada", "Lovelace", "ada@lovelace.com",
				"112-358-1321");

		final PersonAttributesService personAttributesService =
				createMock(PersonAttributesService.class);
		personService.setPersonAttributesService(personAttributesService);
		expect(personAttributesService.getAttributes(TEST_USERNAME)).andReturn(
				adaAttribs);
		replay(personAttributesService);

		final Authentication token = new UsernamePasswordAuthenticationToken(
				TEST_USERNAME, "password");
		final SspUser user = (SspUser) userDetailsService
				.loadUserDetails(token);
		assertNotNull("User should not have been null.", user);
		assertEquals("Usernames did not match.", TEST_USERNAME,
				user.getUsername());

		personDao.delete(user.getPerson());
	}
}