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
	public static String stringFromYear(final int year) {
		return 0 >= year ? "" : String.valueOf(year);
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
}
