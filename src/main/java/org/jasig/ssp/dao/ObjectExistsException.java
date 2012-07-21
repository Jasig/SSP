package org.jasig.ssp.dao;

public class ObjectExistsException extends RuntimeException {

	private static final long serialVersionUID = 6996731597125579874L;

	public ObjectExistsException() {
		super();
	}

	public ObjectExistsException(final String message) {
		super(message);
	}

	public ObjectExistsException(final Throwable cause) {
		super(cause);
	}

	public ObjectExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
