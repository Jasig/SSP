package org.jasig.ssp.config.logging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
public class ExternalConfigLoaderContextListener implements
		ServletContextListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalConfigLoaderContextListener.class);

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final StringBuffer configLocation = new StringBuffer();

		final String environmentDefinedConf = System.getenv("SSP_CONFIGDIR");
		final String contextDefinedConf = sce.getServletContext()
				.getInitParameter("SSP_CONFIGDIR");

		// Give precedence to the environmentDefined Config directory
		if ((environmentDefinedConf == null) && (contextDefinedConf == null)) {
			LOGGER.error("No config directory set");
		} else if (environmentDefinedConf == null) {
			configLocation.append(contextDefinedConf);
		} else {
			configLocation.append(environmentDefinedConf);
		}

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

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		/**
		 * Nothing to do here
		 */
	}
}
