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
package org.jasig.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an
 * {@link org.springframework.security.core.userdetails.UserDetailsService}
 * implementation cannot locate a
 * {@link org.springframework.security.core.userdetails.User}'s record in the
 * database after successful authentication.
 * 
 * @author alexander.leader
 */
public class UserNotAuthorizedException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>UserNotFoundInDatabaseException</code> with the
	 * specified message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public UserNotAuthorizedException(final String msg) {
		super(msg);
	}

	/**
	 * Constructs a <code>UserNotFoundInDatabaseException</code> with the
	 * specified message.
	 *
	 * @param msg
	 *            the detail message.
	 * @param cause
	 *            the originating problem
	 */
	public UserNotAuthorizedException(final String msg, Throwable cause) {
		super(msg, cause);
	}

}
