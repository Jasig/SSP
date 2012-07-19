package org.jasig.ssp.service.impl;

public class PersonAttributesSearchException extends RuntimeException {

	private static final long serialVersionUID = 5028214383991040450L;

	public PersonAttributesSearchException() {
		super();
	}

	public PersonAttributesSearchException(final String message) {
		super(message);
	}

	public PersonAttributesSearchException(final String message,
			final Throwable t) {
		super(message, t);
	}
}
