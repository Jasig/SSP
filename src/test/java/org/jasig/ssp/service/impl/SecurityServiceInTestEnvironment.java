package org.studentsuccessplan.ssp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.security.MockUser;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.SecurityService;

/**
 * A Security Service for use in a test environment. Allows an integration test
 * to directly set the User that will be used in the test.
 * 
 */
public class SecurityServiceInTestEnvironment implements SecurityService {

	private SspUser current;

	private String sessionId;

	@Override
	public SspUser currentUser() {
		return current;
	}

	@Override
	public SspUser currentlyAuthenticatedUser() {
		return current.getUsername().equals(SspUser.ANONYMOUS_PERSON_USERNAME) ? null
				: current;
	}

	@Override
	public SspUser anonymousUser() {
		SspUser user = new SspUser(SspUser.ANONYMOUS_PERSON_USERNAME,
				"", true, true, true, true,
				new ArrayList<GrantedAuthority>(0));
		return user;
	}

	/**
	 * Set the current user.
	 * 
	 * @param current
	 *            Current user
	 */
	public void setCurrent(SspUser current) {
		this.current = current;
	}

	/**
	 * Set the Current user. Will wrap a person domain object in a MockUser. Use
	 * this one if the permissions don't really matter
	 * 
	 * @param current
	 *            Current user
	 */
	public void setCurrent(Person current) {
		if (current.getUsername() == null) {
			current.setUsername("testUser");
		}

		this.current = new MockUser(current, current.getUsername(),
				new ArrayList<GrantedAuthority>());
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
	public void setCurrent(Person current, List<GrantedAuthority> authorities) {
		if (current.getUsername() == null) {
			current.setUsername("testUser");
		}

		this.current = new MockUser(current, current.getUsername(), authorities);
	}

	@Override
	public boolean isAuthenticated() {
		return null != currentlyAuthenticatedUser();
	}

	@Override
	public String getSessionId() {
		return sessionId == null ? "test session id" : sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
