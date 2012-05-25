package org.jasig.ssp.security.exception;

public class UPortalSecurityException extends RuntimeException {

	private static final long serialVersionUID = -99479L;

	public UPortalSecurityException(final String msg) {
		super(msg);
	}

	public UPortalSecurityException(final String msg, final Throwable e) {
		super(msg, e);
	}
}
