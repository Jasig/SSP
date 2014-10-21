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
package org.jasig.ssp.service;

import org.jasig.ssp.security.SspUser;

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
	 * This is similar to {@link #currentUser()} but differs by this method
	 * returning null if the anonymous user is current.
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

	/**
	 * Explicitly return the AnonymousUser
	 * 
	 * @return The anonymous user
	 */
	SspUser anonymousUser();

	boolean hasAuthority(String authority);

	/**
	 * Explicitly return the Admin User with no permissions
	 * 
	 * @return System Admin User
	 */
	SspUser noAuthAdminUser();

	SspUser allConfidentialityLevelsAdminUser();

	/**
	 * Return the current user, or if null, the admin
	 * 
	 * @return the current user, or if null, the admin
	 */
	SspUser currentFallingBackToAdmin();

	/**
	 * Cleanup any request-bound resources that might be bound to the
	 * current security context. This is <em>not</em> a logout. This should
	 * <em>not</em> attempt to replace per-request resource
	 * management already performed by SpringSecurity.
	 */
	void afterRequest();
}