package org.jasig.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an
 * {@link org.springframework.security.core.userdetails.UserDetailsService}
 * implementation cannot locate a
 * {@link org.springframework.security.core.userdetails.User}'s email address.
 * 
 * @author alexander.leader
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
