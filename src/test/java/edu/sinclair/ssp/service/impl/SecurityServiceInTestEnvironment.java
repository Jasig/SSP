package edu.sinclair.ssp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.security.MockUser;
import edu.sinclair.ssp.security.SspUser;
import edu.sinclair.ssp.service.SecurityService;

/**
 * A Security Service for use in a test environment. Allows an integration test
 * to directly set the User that will be used in the test.
 * 
 */
public class SecurityServiceInTestEnvironment implements SecurityService {

	private SspUser current;

	@Override
	public SspUser currentlyLoggedInSspUser() {
		return current;
	}

	/**
	 * Set the current user.
	 */
	public void setCurrent(SspUser current) {
		this.current = current;
	}

	/**
	 * Set the Current user. Will wrap a person domain object in a MockUser. Use
	 * this one if the permissions don't really matter
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
	 * this one if the permissions matter
	 */
	public void setCurrent(Person current, List<GrantedAuthority> authorities) {
		if (current.getUsername() == null) {
			current.setUsername("testUser");
		}

		this.current = new MockUser(current, current.getUsername(), authorities);
	}
}
