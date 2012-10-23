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
/**
 * 
 */
package org.jasig.ssp.web.api.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests on the {@link ValidationException} class.
 * 
 * @author jon.adams
 */
public class ValidationExceptionTest {

	private final static String TEST_MESSAGE = "test message";

	/**
	 * Test method for
	 * {@link org.jasig.ssp.web.api.validation.ValidationException#ValidationException(java.lang.String)}
	 * .
	 */
	@Test
	public void testValidationExceptionString() {
		final Exception obj = new ValidationException(TEST_MESSAGE);
		assertEquals("Messages did not match.", TEST_MESSAGE, obj.getMessage());
		assertNull("Cause should have been null.", obj.getCause());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.web.api.validation.ValidationException#ValidationException(java.lang.String, java.lang.Throwable)}
	 * .
	 */
	@Test
	public void testValidationExceptionStringThrowable() {
		final Exception obj = new ValidationException(TEST_MESSAGE,
				new ValidationException(TEST_MESSAGE));
		assertEquals("Messages did not match.", TEST_MESSAGE, obj.getMessage());
		assertNotNull("Cause should have been null.", obj.getCause());
		assertEquals("Cause.Message did not match.", TEST_MESSAGE, obj
				.getCause().getMessage());
	}
}