package org.jasig.ssp.util.uuid;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * Property Editor for working with UUIDs
 * 
 * @author daniel
 * 
 */
public class UuidPropertyEditor extends PropertyEditorSupport implements
		PropertyEditor {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			setValue(null);
		} else {
			setValue(UUID.fromString(text));
		}
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return null;
		}

		final UUID value = (UUID) getValue();
		return (value == null ? null : value.toString());
	}
}
