package org.jasig.ssp.config.logging;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Simple Utility class for loading an external config file for logback
 * 
 * @author daniel
 */
public class LogBackConfigLoader {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogBackConfigLoader.class);

	/**
	 * Load the Logback Configuration
	 * 
	 * @param configPath
	 *            A config file in an external location
	 * @throws IOException
	 *             If external config file coult not be processed because it
	 *             could not be accessed or read.
	 * @throws JoranException
	 *             If the logback configuration file coult not be processed by
	 *             logback.
	 */
	public LogBackConfigLoader(final String configPath) throws IOException,
			JoranException {
		final LoggerContext loggerContext = (LoggerContext) LoggerFactory
				.getILoggerFactory();

		final File configFile = new File(configPath);
		if (configFile.exists()) {
			if (configFile.isFile()) {
				if (configFile.canRead()) {
					final JoranConfigurator configurator = new JoranConfigurator();
					configurator.setContext(loggerContext);
					loggerContext.reset();
					configurator.doConfigure(configPath);

					LOGGER.info("Configured Logback with config file from: "
							+ configPath);
				} else {
					throw new IOException(
							"Logback External Config File exists and is a file, but cannot be read.");
				}
			} else {
				throw new IOException(
						"Logback External Config File Parameter exists, but does not reference a file");
			}
		} else {
			throw new IOException(
					"Logback External Config File Parameter does not reference a file that exists");
		}
	}

}
