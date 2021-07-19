/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.MockUser;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.SecurityService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Lists;

/**
 * A Security Service for use in a test environment. Allows an integration test
 * to directly set the User that will be used in the test.
 */
public class SecurityServiceInTestEnvironment implements SecurityService, DisposableBean {

	private transient SspUser current;

	private String sessionId;

	@Override
	public SspUser currentUser() {
		return current;
	}

	@Override
	public SspUser currentlyAuthenticatedUser() {
		return current.getUsername().equals(SspUser.ANONYMOUS_PERSON_USERNAME) ? null // NOPMD
				: current;
	}

	@Override
	public SspUser anonymousUser() {
		return new SspUser(SspUser.ANONYMOUS_PERSON_USERNAME,
				"", true, true, true, true,
				new ArrayList<GrantedAuthority>(0));
	}

	@Override
	public SspUser noAuthAdminUser() {
		return new SspUser("no auth admin user", "", false,
				false, false, false, new ArrayList<GrantedAuthority>(0));
	}

	@Override
	public SspUser currentFallingBackToAdmin() {
		final SspUser user = currentUser();

		if (null == user) {
			return noAuthAdminUser();
		} else {
			return user;
		}
	}

	@Override
	public void afterRequest() {
		SspUser.afterRequest();
	}

	/**
	 * Set the current user.
	 * 
	 * @param current
	 *            Current user
	 */
	public void setCurrent(final SspUser current) {
		this.current = current;
	}

	/**
	 * Set the Current user. Will wrap a person domain object in a MockUser. Use
	 * this one if the permissions don't really matter
	 * 
	 * @param current
	 *            Current user
	 */
	public void setCurrent(final Person current) {
		setCurrent(current, new ArrayList<GrantedAuthority>());
	}

	/**
	 * Set the Current user. Will wrap a person domain object in a MockUser. Use
	 * this one if the permissions matter.
	 * 
	 * @param current
	 *            Current user
	 * @param authorities
	 *            List of authorities
	 */
	public void setCurrent(final Person current,
			final Collection<GrantedAuthority> authorities) {
		if (current.getUsername() == null) {
			current.setUsername("testUser");
		}

		this.current = new MockUser(current, current.getUsername(), authorities);
	}

	/**
	 * Sets the specified user with the specified GrantedAuthorities in the
	 * testing authentication session.
	 * 
	 * @param current
	 *            current user
	 * @param authorities
	 *            list of authorities
	 */
	public void setCurrent(final Person current,
			final String... authorities) {
		setCurrent(current, null, authorities);
	}

	/**
	 * Sets the specified user with the specified GrantedAuthorities in the
	 * testing authentication session.
	 * 
	 * @param current
	 *            current user
	 * @param authoritiesCollection
	 *            a list of authorities
	 * @param authorities
	 *            additional authorities
	 */
	public void setCurrent(final Person current,
			final Collection<GrantedAuthority> authoritiesCollection,
			final String... authorities) {

		final List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();

		if (authoritiesCollection != null) {
			grantedAuthorities.addAll(authoritiesCollection);
		}

		for (final String authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority)); // NOPMD
		}

		setCurrent(current, grantedAuthorities);
	}

	@Override
	public boolean isAuthenticated() {
		return null != currentlyAuthenticatedUser();
	}

	@Override
	public String getSessionId() {
		return sessionId == null ? "test session id" : sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public boolean hasAuthority(final String authority) {
		for (final GrantedAuthority auth : currentUser().getAuthorities()) {
			if (auth.getAuthority().equalsIgnoreCase(authority)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() throws Exception {
		SspUser current = currentUser();
		if ( current == null ) {
			return;
		}
		// can't use setCurrent((Person)null): it NPEs.
		// need to make sure SspUser clears its Person, though, b/c it might
		// be stored as an unmanaged ThreadLocal
		current.setPerson(null);
		setCurrent((SspUser)null);
	}

	@Override
	public SspUser allConfidentialityLevelsAdminUser() {
		// TODO Auto-generated method stub
		return null;
	}

}