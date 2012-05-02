package org.jasig.ssp.util.uuid;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

/**
 * Property Editor for working with UUIDs
 * 
 * @author daniel
 * 
 */
public class UuidPropertyEditor extends PropertyEditorSupport implements
		PropertyEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(UUID.fromString(text));
	}

	@Override
	public String getAsText() {
		UUID value = (UUID) getValue();
		return (value != null ? value.toString() : "");
	}
}
