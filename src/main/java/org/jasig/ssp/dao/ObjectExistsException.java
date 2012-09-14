package org.jasig.ssp.dao;

import org.jasig.ssp.util.collections.Pair;

import java.io.Serializable;
import java.util.Map;

public class ObjectExistsException extends RuntimeException {

	private static final long serialVersionUID = 6996731597125579874L;

	private Map<String, ? extends Serializable> lookupFields;

	/**
	 * Entity (class) name
	 */
	private String name;

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

	public ObjectExistsException(final String name,
			final Map<String,? extends Serializable> lookupFields) {
		this(name, lookupFields, null);
	}

	public ObjectExistsException(final String name,
								 final Map<String,? extends Serializable> lookupFields,
								 final Throwable cause) {
		this(message(name, lookupFields), cause);
		this.lookupFields = lookupFields;
		this.name = name;
	}

	/**
	 * Generate a user-readable object exists message string from the fields
	 * used to find that object as well as the object's type.
	 *
	 * @param name the object's type name, usually a fully-qualified class name
	 * @param lookupFields
	 *            Names and values of the criteria that were used to look up
	 *            the object. Typically <code>{"id" => "foo"}</code>
	 * @return A user-readable object found message string.
	 */
	public static String message(final String name,
								 final Map<String,? extends Serializable> lookupFields) {

		final StringBuilder sb = new StringBuilder("Found existing {")
				.append(name == null ? "OBJECT OF UNKNOWN TYPE" : name)
				.append("}. Lookup fields ");
		if ( lookupFields == null || lookupFields.isEmpty() ) {
			sb.append("{UNKNOWN}.");
		} else {
			for ( Map.Entry<String,? extends Serializable> lookupField : lookupFields.entrySet() ) {
				sb.append("{").append(lookupField.getKey()).append(": ")
						.append(lookupField.getValue()).append("} ");
			}
			sb.setCharAt(sb.length() - 1, '.');
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	public void setLookupFields(Map<String, ? extends Serializable> lookupFields) { // NOPMD for serialization
		this.lookupFields = lookupFields;
	}

	public Map<String, ? extends Serializable> getLookupFields() {
		return lookupFields;
	}

	@SuppressWarnings("unused")
	private void setName(final String name) { // NOPMD for serialization
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
