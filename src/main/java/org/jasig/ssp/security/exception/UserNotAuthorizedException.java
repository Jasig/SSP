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

}
