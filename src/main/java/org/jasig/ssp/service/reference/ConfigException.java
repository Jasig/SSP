package org.jasig.ssp.service.reference;

/**
 * Used in conjunction with the ConfigService to throw errors when a property is
 * not found in the database, but should be present.
 * 
 * ConfigService will throw ObjectNotFoundException when a property is optional
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -8942382254216555779L;

	public ConfigException(final String propertyName) {
		super("Expected property " + propertyName
				+ " to be present in the config table, but it was not found. ");
	}

	public ConfigException(final String propertyName, final String message) {
		super("Expected property " + propertyName
				+ " to :" + message);
	}
}
