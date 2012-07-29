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