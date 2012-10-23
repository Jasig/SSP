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
package org.jasig.ssp.service.reference;

import java.util.UUID;

/**
 * Used in conjunction with the ConfigService to throw errors when a property is
 * not found in the database, but should be present.
 * 
 * ConfigService will throw ObjectNotFoundException when a property is optional
 */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -8942382254216555779L;

	public static final String CONFIG_TYPE = "Config";

	public static final String TEMPLATE_TYPE = "Message Template";

	public ConfigException(final String propertyName) {
		super("Expected property " + propertyName
				+ " to be present in the config table, but it was not found. ");
	}

	public ConfigException(final String propertyName, final String message) {
		super("Expected property " + propertyName
				+ " not found in Config table.  Reason:" + message);
	}

	public ConfigException(final UUID propertyId,
			final String configExceptionType, final Throwable t) {
		super("Unable to find " + configExceptionType + " with id: "
				+ propertyId.toString(), t);
	}

	public ConfigException(final String message, final Throwable t) {
		super(message, t);
	}
}
