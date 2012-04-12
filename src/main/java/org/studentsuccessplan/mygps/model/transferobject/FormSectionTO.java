package org.studentsuccessplan.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

public class FormSectionTO {

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
