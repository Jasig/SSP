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
package org.jasig.ssp.util;

/**
 * A few simple helper methods to convert from value types to Strings and vice
 * versa, according to the interfaces in which SSP serializes data.
 */
public final class SspStringUtils {

	/**
	 * This class is only called statically
	 */
	private SspStringUtils() {
		super();
	}

	/**
	 * Convert an integer year to a String.
	 * 
	 * @param year
	 *            Required year value to convert from boolean
	 * @return String representation of the year, or empty string if the value
	 *         was 0 or negative.
	 */
	public static String stringFromYear(final Integer year) {
		return year == null || 0 >= year ? "" : String.valueOf(year);
	}

	/**
	 * Convert a boolean value type to "true" or empty string.
	 * 
	 * @param value
	 *            Required value to convert from boolean
	 * @return "true", or empty string if the value was false.
	 */
	public static String stringFromBoolean(final boolean value) {
		return value ? "true" : "";
	}

	/**
	 * Convert a human-readable boolean value to a boolean value type.
	 * <p>
	 * Acceptable case-insensitive, string representations are:
	 * <ul>
	 * <li>True</li>
	 * <li>T</li>
	 * <li>Yes</li>
	 * <li>Y</li>
	 * <li>1</li>
	 * </ul>
	 * 
	 * @param value
	 *            Required value to convert to boolean
	 * @return True if value could be parsed as a true value; false otherwise.
	 */
	public static boolean booleanFromString(final String value) {
		if ("TRUE".equalsIgnoreCase(value) || "T".equalsIgnoreCase(value)
				|| "YES".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value)
				|| "1".equalsIgnoreCase(value)) {
			return true;
		}

		return false;
	}
	
	public static String yesNoFromBoolean(final Boolean value) {
		return value == null ? null : (value ? "YES" : "NO");
	}

	public static String shortYesNoFromBoolean(final Boolean value) {
		return value == null ? null : (value ? "Y" : "N");
	}
}
