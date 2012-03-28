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

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable t) {
		super(message, t);
	}

}
