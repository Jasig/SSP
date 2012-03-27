package edu.sinclair.ssp.util.uuid;

import java.util.UUID;

/**
 * Utilities for working with UUIDs
 * 
 * @author daniel.bower
 */
public class UuidUtils {

	/**
	 * Check if the parameter is a valid UUID.
	 * 
	 * @param str
	 *            String representation of the UUID.
	 * @return True if the parameter can be parsed into a valid UUID.
	 */
	public static boolean isUUID(String str) {

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
	public static String uuidToString(UUID val) {
		if (null != val) {
			return val.toString();
		} else {
			return "No UUID assigned";
		}
	}
}
