package org.studentsuccessplan.mygps.model.transferobject;

import java.util.UUID;

public class SelfHelpGuideQuestionTO {

	private UUID id;
	private String headingText;
	private String descriptionText;
	private String questionText;
	private boolean mandatory;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getHeadingText() {
		return headingText;
	}
	public void setHeadingText(String headingText) {
		this.headingText = headingText;
	}
	public String getDescriptionText() {
		return descriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

}
