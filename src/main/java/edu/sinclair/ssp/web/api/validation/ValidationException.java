package edu.sinclair.ssp.web.api.validation;

/**
 * Thrown when a business object or TO is invalid
 * 
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	public ValidationException(final String message) {
		super(message);
	}

	public ValidationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
