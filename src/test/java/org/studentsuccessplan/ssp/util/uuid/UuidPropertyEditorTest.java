package edu.sinclair.ssp.util.uuid;

import static org.junit.Assert.*;
import java.beans.PropertyEditor;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class UuidPropertyEditorTest {

	PropertyEditor editor;

	@Before
	public void setup() {
		editor = new UuidPropertyEditor();
	}

	@Test
	public void testGetValue() {
		UUID id = UUID.randomUUID();

		editor.setAsText(id.toString());

		Object val = editor.getValue();

		assertEquals(UUID.class, val.getClass());
		assertEquals(id.toString(), val.toString());
	}

}
