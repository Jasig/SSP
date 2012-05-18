package org.jasig.ssp.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Allows us to read the Spring Profile property from the Config file by
 * reading the SSP_CONFIGDIR environment variable.
 * 
 */
public class BeanProfileApplicationContextInitializer
		implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeanProfileApplicationContextInitializer.class);

	private static final String CONFIG_DIR_NAME = System
			.getenv("SSP_CONFIGDIR");
	private static final String CONFIG_FILE_NAME = "ssp-config.properties";
	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	@Override
	public void initialize(
			final ConfigurableApplicationContext applicationContext) {

		final String propertiesFileName = CONFIG_DIR_NAME
				+ FILE_SEPARATOR + CONFIG_FILE_NAME;

		try {
			applicationContext
					.getEnvironment()
					.getPropertySources()
					.addLast(
							new ResourcePropertySource(
									"file:" + propertiesFileName));
			LOGGER.info("Loaded properties file from " + propertiesFileName
					+ " for determining spring profile.");
		} catch (IOException e) {
			LOGGER.info("Unable to load properties file " + propertiesFileName +
					" in BeanProfileApplicationContextInitializer, defaulting "
					+
					"spring.profile to uportal");
			System.setProperty("spring.profiles.active", "uportal");
		}
	}

}
