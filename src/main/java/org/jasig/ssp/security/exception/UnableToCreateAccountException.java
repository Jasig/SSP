package org.jasig.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UnableToCreateAccountException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UnableToCreateAccountException(final String msg) {
		super(msg);
	}

	public UnableToCreateAccountException(final String msg, final Throwable t) {
		super(msg, t);
	}

}
