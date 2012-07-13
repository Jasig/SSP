package org.jasig.ssp.service.reference.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.service.reference.ConfigException;
import org.junit.Test;

/**
 * Test the {@link ConfigException} class
 */
public class ConfigExceptionTest {

	public static final String TEST_STRING1 = "Test String 1";

	public static final String TEST_STRING2 = "Test String 2";

	public static final String TEST_STRING3 = "Test String 3";

	/**
	 * Test the {@link ConfigException#ConfigException(String)} method.
	 */
	@Test
	public void testConstructor1() {
		// arrange, act
		final ConfigException ce = new ConfigException(TEST_STRING1);

		// assert
		assertTrue("Property should have been included in message.", ce
				.getMessage().contains(TEST_STRING1));
	}

	/**
	 * Test the {@link ConfigException#ConfigException(String, String)} method.
	 */
	@Test
	public void testConstructor2() {
		// arrange, act
		final ConfigException ce = new ConfigException(TEST_STRING1,
				TEST_STRING2);

		// assert
		assertTrue("Property name should have been included in message.", ce
				.getMessage().contains(TEST_STRING1));
		assertTrue("Extra message should have been included in message.", ce
				.getMessage().contains(TEST_STRING2));
	}

	/**
	 * Test the {@link ConfigException#ConfigException(String, Throwable)}
	 * method.
	 */
	@Test
	public void testConstructor3() {
		// arrange
		final ConfigException cause = new ConfigException(TEST_STRING3);

		// act
		final ConfigException ce = new ConfigException(TEST_STRING1, cause);

		// assert
		assertTrue("Property should have been included in message.", ce
				.getMessage().contains(TEST_STRING1));
		assertSame("Cause was not set correctly.", cause, ce.getCause());
	}

	/**
	 * Test the
	 * {@link ConfigException#ConfigException(java.util.UUID, String, Throwable)}
	 * method.
	 */
	@Test
	public void testConstructor4() {
		// arrange
		final UUID id = UUID.randomUUID();
		final ConfigException cause = new ConfigException(TEST_STRING3);

		// act
		final ConfigException ce = new ConfigException(id, TEST_STRING1, cause);

		// assert
		assertTrue("Property should have been included in message.", ce
				.getMessage().contains(TEST_STRING1));
		assertTrue("ID should have been included in message.", ce.getMessage()
				.contains(id.toString()));
		assertSame("Cause was not set correctly.", cause, ce.getCause());
	}
}