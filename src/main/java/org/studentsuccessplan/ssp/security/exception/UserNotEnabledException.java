package org.studentsuccessplan.ssp.security.exception;

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
	public UserNotEnabledException(String msg) {
		super(msg);
	}

}
