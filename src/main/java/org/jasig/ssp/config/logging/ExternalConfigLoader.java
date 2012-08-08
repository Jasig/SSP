package org.jasig.ssp.config.logging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple utility listener to load certain properties before Spring Starts up.
 * 
 * Add this entry to your web.xml:
 * 
 * <pre>
 * <listener>
 *     <listener-class>org.jasig.ssp.config.logging.ExternalConfigLoaderContextListener</listener-class>
 *   </listener>
 * </pre>
 */
public class ExternalConfigLoader implements
		ServletContextListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalConfigLoader.class);

	private static String configDir;

	private final boolean initLogback = true;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		initConfigDirectory(sce);
		initLogConfig();
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		/**
		 * Nothing to do here
		 */
	}

	public static String getConfigDir() {
		return configDir;
	}

	private void initConfigDirectory(final ServletContextEvent sce) {
		final StringBuffer configLocation = new StringBuffer();

		final String environmentDefinedConf = System.getenv("SSP_CONFIGDIR");

		if (StringUtils.isBlank(environmentDefinedConf)) {

			final String contextDefinedConf = sce.getServletContext()
					.getInitParameter("SSP_CONFIGDIR");

			if (StringUtils.isBlank(contextDefinedConf)) {
				LOGGER.error("Unable to set config directory.");
			} else {
				configLocation.append(contextDefinedConf);
			}

		} else {
			configLocation.append(environmentDefinedConf);
		}

		configDir = configLocation.toString();

		LOGGER.info("Config directory set to {}", configDir);
	}

	private void initLogConfig() {
		if (!initLogback) {
			return;
		}

		final StringBuffer configLocation = new StringBuffer();
		configLocation.append(configDir);
		configLocation.append(System.getProperty("file.separator"));
		configLocation.append("logback.xml");

		try {
			new LogBackConfigLoader(configLocation.toString());
		} catch (Exception e) {
			LOGGER.error(
					"Unable to read config file from "
							+ configLocation.toString(), e);
		}
	}
}
