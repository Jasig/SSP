package org.studentsuccessplan.ssp.service;

import org.studentsuccessplan.ssp.security.SspUser;

/**
 * Security service to provide authenticated user and session information.
 */
public interface SecurityService {

	/**
	 * Retrieve the current user (authenticated or anonymous).
	 * 
	 * @return The current user, or null if there was some kind of error or
	 *         missing data.
	 * @see #currentlyAuthenticatedUser()
	 */
	SspUser currentUser();

	/**
	 * Returns currently authenticated user, or null if this session has not
	 * been authenticated.
	 * <p>
	 * This is similar to {@link #currentUser()} but differs by
	 * this method returning null if the anonymous user is current.
	 * 
	 * @return The currently authenticated user, or null if this session has not
	 *         been authenticated.
	 * @see #currentUser()
	 */
	SspUser currentlyAuthenticatedUser();

	/**
	 * Check if this session has been authenticated.
	 * <p>
	 * Will return false if the anonymous user is current instead of an
	 * authenticated user.
	 * 
	 * @return True if this session has been authenticated by any valid, active
	 *         user; false for the anonymous user.
	 */
	boolean isAuthenticated();

	/**
	 * Get the session identifier.
	 * 
	 * @return The session identifier.
	 */
	String getSessionId();
}
