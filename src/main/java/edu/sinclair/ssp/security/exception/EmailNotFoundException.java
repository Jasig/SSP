package edu.sinclair.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an {@link UserDetailsService} implementation cannot locate a
 * {@link User}'s email address.
 * 
 * @author Alexander Leader
 */
public class EmailNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>EmailNotFoundException</code> with the specified
	 * message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public EmailNotFoundException(String msg) {
		super(msg);
	}

}
