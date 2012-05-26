package org.jasig.mygps.model.transferobject;

import java.io.Serializable;

/**
 * Message transfer object
 */
public class MessageTO implements Serializable {

	private static final long serialVersionUID = 1454568554970597597L;

	private String subject;

	private String message;

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}