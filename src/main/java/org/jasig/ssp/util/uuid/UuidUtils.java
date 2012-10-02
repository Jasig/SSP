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
