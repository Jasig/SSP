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