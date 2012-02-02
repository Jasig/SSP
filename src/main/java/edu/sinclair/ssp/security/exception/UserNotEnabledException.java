package edu.sinclair.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an {@link UserDetailsService} implementation checks the {@link Affiliation} and it is not enabled.
 *
 * @author Alexander Leader
 */
public class UserNotEnabledException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	 /**
     * Constructs a <code>UserNotEnabledException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    public UserNotEnabledException(String msg) {
        super(msg);
    }

}
