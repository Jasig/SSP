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

public class FormSectionTO implements Serializable {

	private static final long serialVersionUID = 3931116434291670539L;

	private UUID id;

	private String label;

	private List<FormQuestionTO> questions;

	public FormQuestionTO getFormQuestionById(UUID formQuestionId) {
		for (FormQuestionTO formQuestionTO : getQuestions()) {
			if (formQuestionTO.getId().equals(formQuestionId)) {
				return formQuestionTO;
			}
		}
		return null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<FormQuestionTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<FormQuestionTO> questions) {
		this.questions = questions;
	}
}
