package org.jasig.ssp.util.uuid;

import java.util.UUID;

/**
 * Utilities for working with UUIDs
 * 
 * @author daniel.bower
 */
public final class UuidUtils {

	/**
	 * This class is only called statically
	 */
	private UuidUtils() {
		super();
	}

	/**
	 * Check if the parameter is a valid UUID.
	 * 
	 * @param str
	 *            String representation of the UUID.
	 * @return True if the parameter can be parsed into a valid UUID.
	 */
	public static boolean isUUID(final String str) {

		if (str == null) {
			return false;
		} else if (str.length() != 36) {
			return false;
		} else {
			try {
				UUID.fromString(str);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Turn the parameter into a String.
	 * 
	 * @param val
	 *            UUID to convert to a String.
	 * @return String representation of the passed UUID.
	 */
	public static String uuidToString(final UUID val) {
		if (null == val) {
			return "No UUID assigned";
		} else {
			return val.toString();
		}
	}
}
