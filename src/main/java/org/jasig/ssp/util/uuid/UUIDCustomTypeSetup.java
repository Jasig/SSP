package org.jasig.ssp.util.uuid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Provide Spring support for loading a configuration variable that will be used
 * to inject static properties into the {@link UUIDCustomType} class that
 * Hibernate will initialize.
 * 
 * @author jon.adams
 * 
 */
public final class UUIDCustomTypeSetup {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UUIDCustomTypeSetup.class);

	@Value("#{configProperties.db_dialect}")
	private String dialect;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(final String dialect) {
		this.dialect = dialect;
	}

	/**
	 * This method bust be called in the Spring configuration so that it can
	 * inject the dialect property into the {@link UUIDCustomType} statics.
	 */
	public void initSettings() {
		LOGGER.info("Initializing UUIDCustomType with dialect: " + dialect);
		UUIDCustomType.initSettings(dialect);
	}
}