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
package org.jasig.ssp.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests on {@link ComponentException}.
 * 
 * @author jon.adams
 */
public class ComponentExceptionTest {

	private static final String TEST_MESSAGE = "more message";

	private static final String TEST_INNER_MESSAGE = "inner";

	@Test
	public void test1ParamConstructor() {
		// arrange
		final ComponentException exc = new ComponentException(
				TEST_MESSAGE);

		// assert
		assertTrue("Messages was not correctly included.", exc.getMessage()
				.contains(TEST_MESSAGE));
	}

	@Test
	public void test2ParamConstructor() {
		// arrange
		final ComponentException exc = new ComponentException(
				TEST_MESSAGE, new ComponentException(TEST_INNER_MESSAGE));

		// assert
		assertTrue("Messages was not correctly included.", exc.getMessage()
				.contains(TEST_MESSAGE));
		assertNotNull("Messages did include inner exception.", exc.getCause());
	}
}