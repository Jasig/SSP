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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * Property Editor for working with UUIDs
 * 
 * @author daniel.bower
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