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
 * This exception is thrown if an
 * {@link org.springframework.security.core.userdetails.UserDetailsService}
 * implementation checks the <code>Affiliation</code> and it is not enabled.
 * 
 * @author alexander.leader
 */
public class UserNotEnabledException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>UserNotEnabledException</code> with the specified
	 * message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public UserNotEnabledException(final String msg) {
		super(msg);
	}

}
