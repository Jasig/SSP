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
import java.util.Locale;

import org.jasig.ssp.util.SspTimeZones;

/**
 * Utilities related to JSON rendering of date/time values. Need to be static
 * methods/fields rather than an injectable Spring bean b/c these functions
 * are intended for use in Jackson-instantiated de/serializers. Note that
 * there <em>is</em> some Spring configuration going on in the current
 * implementation though, b/c we depend on {@link SspTimeZones}, which, while
 * accessed via static field, is typically Spring-initialized.
 *
 * <p>Currently really only relevant to handling of date-only values, i.e.
 * values where just where the time component isn't just unimportant, but where
 * the time component it needs to be actively ignored lest the date component be
 * rendered in the wrong timezone. But we can't actually ignore the time
 * component in a <code>java.util.Date</code>, so to get this right, we need to
 * have the db and JSON layers agree on what timezone these values
 * <em>should</em> be rendered in</p>. So this class is actually just a thin
 * wrapper on {@link SspTimeZones} with some API-specific knowledge about date
 * formats in our JSON APIs.</p>
 */
public class DateTimeRepresentation {

	/**
	 * Don't change this. Actually part of the web API.
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static SimpleDateFormat newDateFormatter() {
		return newDateFormatter(null);
	}

	public static SimpleDateFormat newDateFormatter(String pattern) {
		return newDateFormatter(pattern, null);
	}

	public static SimpleDateFormat newDateFormatter(String pattern, Locale locale) {
		SimpleDateFormat sdf =
				new SimpleDateFormat(defaultPatternIfNull(pattern),
						defaultLocaleIfNull(locale));
		sdf.setTimeZone(SspTimeZones.INSTANCE.getDbTimeZone());
		return sdf;
	}

	private static String defaultPatternIfNull(String pattern) {
		return pattern == null ? DEFAULT_DATE_PATTERN : pattern;
	}

	private static Locale defaultLocaleIfNull(Locale locale) {
		return locale == null ? Locale.getDefault() : locale;
	}

}
