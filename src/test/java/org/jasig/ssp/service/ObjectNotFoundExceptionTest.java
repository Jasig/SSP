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

	@Test
	public void test3ParamConstructor() {
		// arrange
		final ObjectNotFoundException exc = new ObjectNotFoundException(
				UUID.randomUUID(), "entity", "wow",
				new ObjectNotFoundException("message", "name"));

		// assert
		assertNotNull("UUID should not have been null.", exc.getObjectId());
		assertEquals("Names did not match.", "entity", exc.getName());
		assertTrue("Messages did not include ID.",
				exc.getMessage().contains(exc.getObjectId().toString()));
		assertTrue("Messages did not include entity name.", exc.getMessage()
				.contains("entity"));
		assertTrue("Messages did include extra message.", exc.getMessage()
				.contains("wow"));
	}
}