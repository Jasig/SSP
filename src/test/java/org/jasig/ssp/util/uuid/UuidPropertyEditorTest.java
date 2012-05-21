package org.jasig.ssp.util.uuid;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyEditor;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests on {@link UuidPropertyEditor}
 */
public class UuidPropertyEditorTest {

	private transient PropertyEditor editor;

	@Before
	public void setUp() {
		editor = new UuidPropertyEditor();
	}

	@Test
	public void testGetValue() {
		final UUID id = UUID.randomUUID();

		editor.setAsText(id.toString());

		final Object val = editor.getValue();

		assertEquals("Class name did not match.", UUID.class, val.getClass());
		assertEquals("IDs did not match.", id.toString(), val.toString());
	}

	@Test
	public void testGetAsText() {
		final UUID id = UUID.randomUUID();
		editor.setAsText(id.toString());

		assertEquals("AsText() output did not match.", id.toString(),
				editor.getAsText());

		editor.setAsText(null);
		assertEquals("AsText(null) output did not return null.", null,
				editor.getAsText());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidId() {
		editor.setAsText("invalid UUID");

		assertEquals("Invalid UUID should have returned null.", null,
				editor.getAsText());
	}
}