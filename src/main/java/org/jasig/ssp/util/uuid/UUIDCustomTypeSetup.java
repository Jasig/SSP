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