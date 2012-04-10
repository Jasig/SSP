package edu.sinclair.ssp.config.logging;

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
 *     <listener-class>edu.sinclair.ssp.config.logging.ExternalConfigLoaderContextListener</listener-class>
 *   </listener>
 * </pre>
 * 
 * @author daniel
 * 
 */
public class ExternalConfigLoaderContextListener implements
		ServletContextListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalConfigLoaderContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String configLocation = sce.getServletContext().getInitParameter(
				"SSP_CONFIGDIR");
		if (configLocation == null) {
			configLocation = System.getenv("SSP_CONFIGDIR");
		}

		try {
			new LogBackConfigLoader(configLocation + "logback.xml");
		} catch (Exception e) {
			LOGGER.error("Unable to read config file", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
