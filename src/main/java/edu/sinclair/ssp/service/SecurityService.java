package edu.sinclair.ssp.service;

import edu.sinclair.ssp.security.SspUser;

/**
 * Security service to provide authenticated user and session information.
 */
public interface SecurityService {

	/**
	 * Returns currently authenticated user, or null if this session has not
	 * been authenticated.
	 * 
	 * @return The currently authenticated user, or null if this session has not
	 *         been authenticated.
	 */
	public SspUser currentlyLoggedInSspUser();

	/**
	 * Check if this session has been authenticated.
	 * 
	 * @return True if this session has been authenticated by any valid, active
	 *         user.
	 */
	public boolean isAuthenticated();

	/**
	 * Get the session identifier.
	 * 
	 * @return The session identifier.
	 */
	public String getSessionId();
}
