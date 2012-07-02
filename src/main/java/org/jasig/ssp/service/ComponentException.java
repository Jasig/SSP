package org.jasig.ssp.service;

public class ComponentException extends RuntimeException {

	private static final long serialVersionUID = -587117706993322576L;

	public ComponentException(final String message) {
		super(message);
	}

	public ComponentException(final String message, final Throwable t) {
		super(message, t);
	}
}
