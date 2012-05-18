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