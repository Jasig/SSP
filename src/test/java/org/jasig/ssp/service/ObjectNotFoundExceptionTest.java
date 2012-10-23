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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

/**
 * @author jon.adams
 * 
 */
public class ObjectNotFoundExceptionTest {

	private static final String TEST_ENTITY_NAME = "entity";

	private static final String TEST_MESSAGE = "more message";

	private static final String TEST_INNER_ENTITY_NAME = "model";

	private static final String TEST_INNER_MESSAGE = "inner";

	@Test
	public void test2ParamConstructor() {
		// arrange
		final ObjectNotFoundException exc = new ObjectNotFoundException(
				UUID.randomUUID(), TEST_ENTITY_NAME);

		// assert
		assertNotNull("UUID should not have been null.", exc.getObjectId());
		assertEquals("Names did not match.", TEST_ENTITY_NAME, exc.getName());
		assertTrue("Messages did not include ID.",
				exc.getMessage().contains(exc.getObjectId().toString()));
		assertTrue("Messages did not include entity name.", exc.getMessage()
				.contains(TEST_ENTITY_NAME));
	}

	@Test
	public void test3ParamConstructor() {
		// arrange
		final ObjectNotFoundException exc = new ObjectNotFoundException(
				UUID.randomUUID(), TEST_ENTITY_NAME,
				new ObjectNotFoundException(TEST_INNER_MESSAGE,
						TEST_INNER_ENTITY_NAME));

		// assert
		assertNotNull("UUID should not have been null.", exc.getObjectId());
		assertEquals("Names did not match.", TEST_ENTITY_NAME, exc.getName());
		assertTrue("Messages did not include ID.",
				exc.getMessage().contains(exc.getObjectId().toString()));
		assertTrue("Messages did not include entity name.", exc.getMessage()
				.contains(TEST_ENTITY_NAME));
	}

	@Test
	public void test4ParamConstructor() {
		// arrange
		final ObjectNotFoundException exc = new ObjectNotFoundException(
				UUID.randomUUID(), TEST_ENTITY_NAME, TEST_MESSAGE,
				new ObjectNotFoundException(TEST_INNER_MESSAGE,
						TEST_INNER_ENTITY_NAME));

		// assert
		assertNotNull("UUID should not have been null.", exc.getObjectId());
		assertEquals("Names did not match.", TEST_ENTITY_NAME, exc.getName());
		assertTrue("Messages did not include ID.",
				exc.getMessage().contains(exc.getObjectId().toString()));
		assertTrue("Messages did not include entity name.", exc.getMessage()
				.contains(TEST_ENTITY_NAME));
		assertTrue("Messages did include extra message.", exc.getMessage()
				.contains(TEST_MESSAGE));
	}
}