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
package org.jasig.ssp.util.assertions;

import java.util.Collection;

import org.junit.ComparisonFailure;

/**
 * @author jon.adams
 * 
 */
public class SspAssert extends org.junit.Assert { // NOPMD by jon.adams
	/**
	 * Asserts that two objects are not equal. If they are, an
	 * {@link AssertionError} is thrown with the given message. If
	 * <code>expected</code> and <code>actual</code> are both <code>null</code>,
	 * they are considered equal and will therefore also fail this assertion.
	 * 
	 * @param message
	 *            the identifying message for the {@link AssertionError} (
	 *            <code>null</code> okay)
	 * @param expected
	 *            expected value
	 * @param actual
	 *            actual value
	 */
	static public void assertNotEquals(final String message,
			final Object expected,
			final Object actual) {
		if (expected == null && actual == null) {
			return;
		}
		if (expected != null && isNotEquals(expected, actual)) {
			return;
		} else if (expected instanceof String && actual instanceof String) {
			final String cleanMessage = message == null ? "" : message;
			throw new ComparisonFailure(cleanMessage, (String) expected,
					(String) actual);
		} else {
			fail(format(
					message,
					"inequality",
					"both "
							+ formatClassAndValue(actual,
									String.valueOf(actual))));
		}
	}

	/**
	 * Asserts that a Collection is not null and not empty. If not,
	 * {@link AssertionError} is thrown with the given message.
	 * 
	 * @param message
	 *            the identifying message for the {@link AssertionError} (
	 *            <code>null</code> okay)
	 * @param actual
	 *            actual Collection
	 */
	static public void assertNotEmpty(final String message,
			final Collection<?> actual) {
		if (actual != null && !actual.isEmpty()) {
			return;
		}

		fail(format(message, "not null and not empty", actual == null ? "null"
				: "containing <" + actual.size() + "> item"
						+ (actual.size() == 1 ? "" : "s")));
	}

	private static boolean isNotEquals(final Object expected,
			final Object actual) {
		return !expected.equals(actual);
	}

	private static String format(final String message, final Object expected,
			final Object actual) {
		String formatted = ""; // NOPMD
		if (message != null && !message.equals("")) {
			formatted = message + " ";
		}
		final String expectedString = String.valueOf(expected);
		final String actualString = String.valueOf(actual);
		if (expectedString.equals(actualString)) {
			return formatted + "expected "
					+ expectedString
					+ " but was: " + actualString;
		} else {
			return formatted + "expected " + expectedString + " but was: "
					+ actualString;
		}
	}

	private static String formatClassAndValue(final Object value,
			final String valueString) {
		final String className = value == null ? "null" : value.getClass()
				.getName();
		return className + "<" + valueString + ">";
	}
}