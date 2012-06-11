package org.jasig.ssp.service;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

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

	/**
	 * Constructs a new exception with the detail message generated from the
	 * specified parameters.
	 * 
	 * @param objectId
	 *            Identifier that could not be found
	 * @param name
	 *            Entity name that did not contain the specified objectId
	 */
	public ObjectNotFoundException(final UUID objectId, final String name) {
		this(message(objectId, name));
		this.objectId = objectId;
		this.name = name;
	}

	/**
	 * Constructs a new exception with the detail message generated from the
	 * specified parameters.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 * @param name
	 *            Entity name that did not contain the specified objectId
	 */
	public ObjectNotFoundException(final String message, final String name) {
		this(message);
		this.name = name;
	}

	/**
	 * Constructs a new exception with the detail message generated from the
	 * specified parameters, and cause.
	 * 
	 * <p>
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param objectId
	 *            Identifier that could not be found
	 * @param name
	 *            Entity name that did not contain the specified objectId
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public ObjectNotFoundException(final UUID objectId, final String name,
			final Throwable cause) {
		this(message(objectId, name), cause);
		this.objectId = objectId;
		this.name = name;
	}

	/**
	 * Constructs a new exception with the detail message generated from the
	 * specified parameters, and cause.
	 * 
	 * <p>
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param objectId
	 *            Identifier that could not be found
	 * @param name
	 *            Entity name that did not contain the specified objectId
	 * @param message
	 *            Additional message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public ObjectNotFoundException(final UUID objectId, final String name,
			final String message, final Throwable cause) {
		this(message(objectId, name) + ". " + message, cause);
		this.objectId = objectId;
		this.name = name;
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized, and may subsequently be initialized by a call to
	 * initCause.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	private ObjectNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * <p>
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param message
	 *            message the detail message (which is saved for later retrieval
	 *            by the {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	private ObjectNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Generate a user-readable object not found message string.
	 * 
	 * @param objectId
	 *            Identifier that could not be found
	 * @param name
	 *            Entity name that did not contain the specified objectId
	 * @return A user-readable object not found message string.
	 */
	private static String message(final UUID objectId,
			@NotNull final String name) {
		return "Unable to access {" + name + "} with ID {"
				+ (objectId == null ? "null" : objectId.toString() + "}");
	}

	@SuppressWarnings("unused")
	private void setObjectId(final UUID objectId) { // NOPMD for serialization
		this.objectId = objectId;
	}

	public UUID getObjectId() {
		return objectId;
	}

	@SuppressWarnings("unused")
	private void setName(final String name) { // NOPMD for serialization
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
