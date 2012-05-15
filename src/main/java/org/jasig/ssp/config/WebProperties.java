package org.jasig.ssp.config;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class will pass through specific Configuration properties to the View
 * Layer
 * 
 * #suboptimal - This class could definitely use some additional
 * research, but at least it works for now.
 */

@Service
public class WebProperties {

	private static WebProperties webProps;

	/**
	 * Create singleton-ish properties class
	 */
	public WebProperties() {
		super();
		if (webProps == null) {
			webProps = this;
		} else {
			LOGGER.warn("WebProperites was instantiated twice.  This shouldn't hurt anything, but may indicate an error condition in the application");
		}
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebProperties.class);

	@Autowired
	private transient ConfigService configService;

	public static WebProperties getInstance() {
		if (webProps.configService == null) {
			throw new IllegalArgumentException(
					"WebProperties Service was not properly instantiated - The ConfigServe is not available (null)");
		}

		return webProps;
	}

	private static final String APP_TITLE = "app_title";
	private static final String INST_HOME_URL = "inst_home_url";

	public String getAppTitle() {
		return getProperty(APP_TITLE);
	}

	public String getInstHomeUrl() {
		return getProperty(INST_HOME_URL);
	}

	/**
	 * Leaving this method private because we want to restrict access to some
	 * data - Only data which has a specific accessor is allowed to be accessed
	 * in the web interface
	 * 
	 * @param propertyName
	 *            - key of the config object in the config table
	 * @return value of the config object in the config table - empty string if
	 *         none found
	 */
	private String getProperty(final String propertyName) {
		try {
			return configService.getByNameException(propertyName);
		} catch (ObjectNotFoundException e) {
			LOGGER.error(propertyName + " not defined");
		}

		return "";
	}

}
