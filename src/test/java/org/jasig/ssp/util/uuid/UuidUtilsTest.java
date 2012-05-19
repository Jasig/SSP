package org.jasig.ssp.util.uuid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

public class UuidUtilsTest {

	@Test
	public void testIsUuid() {
		final UUID id = UUID.randomUUID();

		assertTrue("Should have returned true.",
				UuidUtils.isUUID("1f5729af-0337-4e58-a001-8a9f80dbf8aa"));
		assertTrue("Should have returned true.",
				UuidUtils.isUUID(id.toString()));
		assertFalse("Should have returned false.",
				UuidUtils.isUUID("1f5729af03374e58a0018a9f80dbf8aa"));
		assertFalse("Empty string have returned false.",
				UuidUtils.isUUID(""));
		assertFalse("Null have returned false.",
				UuidUtils.isUUID(null));
		assertFalse(
				"Invalid string but with same character count should have returned false.",
				UuidUtils.isUUID("1f5729aft0337t4e58ta001t8a9f80dbf8aa"));
	}

	@Test
	public void testUuidToString() {
		final UUID id = UUID.fromString("1f5729af-0337-4e58-a001-8a9f80dbf8aa");

		assertEquals("UUID from string and back out again didn't match.",
				"1f5729af-0337-4e58-a001-8a9f80dbf8aa",
				UuidUtils.uuidToString(id));

		assertEquals("Null response did match.", "No UUID assigned",
				UuidUtils.uuidToString(null));
	}
}