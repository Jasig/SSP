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
package org.jasig.ssp.transferobject.jsonserializer;

import java.text.SimpleDateFormat;

/**
 * Hosts shared formatting features for Jackson JSON date-only de/serialization.
 */
public class DateOnlyFormatting {

	/**
	 * Don't change this. Actually part of the web API.
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * Returns a formatter/parser using {@link #DEFAULT_DATE_PATTERN},
	 * configured with the JVM default timezone. We always use the JVM default
	 * timezone to interpret date-only time values, both in the JSON API and
	 * the db layer.
	 *
	 * @return
	 */
	public static SimpleDateFormat dateFormatter() {
		return new SimpleDateFormat(DEFAULT_DATE_PATTERN);
	}

}
