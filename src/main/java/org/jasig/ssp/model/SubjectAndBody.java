package org.jasig.ssp.model;

/**
 * Simple container for the Subject and Body of a message.
 */
public class SubjectAndBody {

	private final String subject; // NOPMD - immutable

	private final String body; // NOPMD - immutable

	public SubjectAndBody(final String subject, final String message) {
		this.subject = subject;
		this.body = message;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}
}
