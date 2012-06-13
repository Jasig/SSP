package org.jasig.ssp.service.reference;

import java.util.UUID;

/**
 * Used in conjunction with the ConfigService to throw errors when a property is
 * not found in the database, but should be present.
 * 
 * ConfigService will throw ObjectNotFoundException when a property is optional
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -8942382254216555779L;

	public static final String CONFIG_TYPE = "Config";
	public static final String TEMPLATE_TYPE = "Message Template";

	public ConfigException(final String propertyName) {
		super("Expected property " + propertyName
				+ " to be present in the config table, but it was not found. ");
	}

	public ConfigException(final String propertyName, final String message) {
		super("Expected property " + propertyName
				+ " to :" + message);
	}

	public ConfigException(final UUID propertyId,
			final String configExceptionType, final Throwable t) {
		super("Unable to find " + configExceptionType + " with id: "
				+ propertyId.toString(), t);
	}
}
