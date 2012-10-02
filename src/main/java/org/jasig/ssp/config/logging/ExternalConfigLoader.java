/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
