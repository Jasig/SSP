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
