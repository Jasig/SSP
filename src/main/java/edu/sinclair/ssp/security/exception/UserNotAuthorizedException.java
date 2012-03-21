package edu.sinclair.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an {@link UserDetailsService} implementation cannot locate a
 * {@link User}'s record in the database after successful authentication.
 * 
 * @author Alexander Leader
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
	public UserNotAuthorizedException(String msg) {
		super(msg);
	}

}
