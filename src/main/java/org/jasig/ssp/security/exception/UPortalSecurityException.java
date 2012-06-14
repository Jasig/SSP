package org.jasig.ssp.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UPortalSecurityException extends AuthenticationException {

	private static final long serialVersionUID = -99479L;

	public UPortalSecurityException(final String msg) {
		super(msg);
	}

	public UPortalSecurityException(final String msg, final Throwable e) {
		super(msg, e);
	}
}
