package org.jasig.ssp.config;

import org.jasig.ssp.config.logging.ExternalConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Allows us to read the Spring Profile property from the Config file by
 * building the location of the config files with the ExternalConfigLoader.
 * 
 */
public class BeanProfileApplicationContextInitializer
		implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeanProfileApplicationContextInitializer.class);

	private static final String CONFIG_FILE_NAME = "ssp-config.properties";
	private static final String DEFAULT_CONFIG_FILE_NAME =
			"ssp-config.default.properties";
	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	@Override
	public void initialize(
			final ConfigurableApplicationContext applicationContext) {

		final String propertiesFileName = ExternalConfigLoader
				.getConfigDir() + FILE_SEPARATOR + CONFIG_FILE_NAME;

		boolean anyPropsLoaded = false;
		final String defaultPropertiesResourcePath =
				"classpath:"+DEFAULT_CONFIG_FILE_NAME;
		try {
			applicationContext
					.getEnvironment()
					.getPropertySources()
					.addLast(new ResourcePropertySource(
							defaultPropertiesResourcePath));
			anyPropsLoaded = true;
			LOGGER.info("Loaded properties file from {} for determining "
					+ "spring profile.", defaultPropertiesResourcePath);
		} catch ( Exception e ) {
			LOGGER.info("Unable to load properties file {} for determining "
					+ " spring profile.", defaultPropertiesResourcePath, e);
		}
		try {
			applicationContext
					.getEnvironment()
					.getPropertySources()
					.addLast(
							new ResourcePropertySource(
									"file:" + propertiesFileName));
			anyPropsLoaded = true;
			LOGGER.info("Loaded properties file from {} for determining "
					+ "spring profile.", propertiesFileName);
		} catch (Exception e) {
			LOGGER.info("Unable to load properties file {} for determining "
					+ " spring profile.", propertiesFileName, e);
		}

		if ( !(anyPropsLoaded) ) {
			LOGGER.info("Unable to load any configuration files for "
					+ "determining Spring profile. Defaulting spring.profile " +
					 "to uportal");
			System.setProperty("spring.profiles.active", "uportal");
		}

	}

}
