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
package org.jasig.mygps.model.transferobject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Student Intake Form transfer object
 */
public class FormTO implements Serializable {

	private static final long serialVersionUID = -1978459152865190470L;

	private UUID id;

	private String label;

	private List<FormSectionTO> sections;
	
	private boolean completed;
	

	public FormSectionTO getFormSectionById(final UUID formSectionId) {
		for (final FormSectionTO formSectionTO : getSections()) {
			if (formSectionTO.getId().equals(formSectionId)) {
				return formSectionTO;
			}
		}
		return null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public List<FormSectionTO> getSections() {
		return sections;
	}

	public void setSections(final List<FormSectionTO> sections) {
		this.sections = sections;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}