package edu.sinclair.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

public class FormSectionTO {

	private UUID id;
	private String label;
	private List<FormQuestionTO> questions;

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
