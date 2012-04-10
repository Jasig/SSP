package edu.sinclair.ssp.service;

import java.io.Serializable;
import java.util.UUID;

/**
 * A record was not found in persistent storage based on the specified
 * identifier.
 */
public class ObjectNotFoundException extends Exception implements Serializable {

	private static final long serialVersionUID = -4359471388801312813L;

	/**
	 * Identifier that was used for the lookup that failed.
	 */
	private UUID objectId;

	/**
	 * Entity (class) name
	 */
	private String name;

	public ObjectNotFoundException(final UUID objectId, final String name) {
		super(message(objectId, name));
		this.objectId = objectId;
		this.name = name;
	}

	public ObjectNotFoundException(final String message) {
		super(message);
	}

	private static String message(final UUID objectId, final String name) {
		return "Unable to access " + name + " with id " + objectId.toString();
	}

	public UUID getObjectId() {
		return objectId;
	}

	public String getName() {
		return name;
	}
}
