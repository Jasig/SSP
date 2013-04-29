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
package org.jasig.ssp.config;


import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

// TODO eerily similar to PlaceholderDereferencingPropertiesFactoryBean. Anything refactorable.
public class ConfigPostProcessingPropertiesFactoryBean
		implements FactoryBean<Properties> {

	public static final String LEGACY_TIMEZONE_PROP = "db_time_zone_legacy";
	public static final String LOOKUP_LEGACY_TIMEZONE = "CURRENT_JVM_DEFAULT";

	private Properties properties;
	private Properties postProcessedProperties;

	public ConfigPostProcessingPropertiesFactoryBean() {}

	public ConfigPostProcessingPropertiesFactoryBean(Properties properties) {
		this.properties = properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getObject() throws Exception {
		if ( postProcessedProperties == null ) {
			postProcessProperties();
		}
		return postProcessedProperties;
	}

	private void postProcessProperties() {
		postProcessedProperties = new Properties();
		postProcessedProperties.putAll(properties);
		String origVal = StringUtils.trimToNull(postProcessedProperties.getProperty(LEGACY_TIMEZONE_PROP));
		if ( origVal == null || LOOKUP_LEGACY_TIMEZONE.equals(origVal) ) {
			postProcessedProperties.setProperty(LEGACY_TIMEZONE_PROP, TimeZone.getDefault().getID());
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
