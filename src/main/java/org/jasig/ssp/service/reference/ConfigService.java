package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ReferenceService;

/**
 * Configuration (Config) service
 * 
 * @author daniel.bower
 */
public interface ConfigService extends
		ReferenceService<Config> {

	/**
	 * Gets the specified configuration value, or return empty if not found.
	 * 
	 * @param name
	 *            Key for the specific configuration value to load
	 * @return the specified configuration key, or empty if not found.
	 */
	String getByNameEmpty(String name);

	/**
	 * Gets the specified configuration value, or throw an exception if not
	 * found.
	 * 
	 * @param name
	 *            Key for the specific configuration value to load
	 * @return the specified configuration key, or throws an exception if not
	 *         found.
	 * @throws ObjectNotFoundException
	 *             If specified configuration key could not be found
	 */
	String getByNameException(String name) throws ObjectNotFoundException;

	/**
	 * Gets the specified configuration value, or return null if not found.
	 * 
	 * @param name
	 *            Key for the specific configuration value to load
	 * @return the specified configuration key, or null if not found.
	 */
	String getByNameNull(String name);

	/**
	 * Get a String Config Value by name.
	 * 
	 * @param name
	 *            of the config value
	 * @return the default if the value is empty, or null if it is not found
	 */
	String getByNameNullOrDefaultValue(String name);

	/**
	 * Get an int Config Value by name.
	 * 
	 * @param name
	 *            of the config value
	 * @return value or default value if not an integer
	 * @throws ConfigException
	 *             A runtimeException if the value is not found
	 */
	int getByNameExceptionOrDefaultAsInt(String name);

	/**
	 * The database-specific CONCAT operator, because some databases don't
	 * accept ANSI SQL.
	 * 
	 * @return database-specific CONCAT operator
	 */
	String getDatabaseConcatOperator();
}