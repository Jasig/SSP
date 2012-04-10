package org.studentsuccessplan.ssp.model.reference;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import org.studentsuccessplan.ssp.transferobject.jsonserializer.CodeAndProperty;
import org.studentsuccessplan.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum EmploymentShifts implements CodeAndProperty {
	FIRST("1st"), SECOND("2nd"), THIRD("3rd"), NOT_APPLICABLE("Not Applicable");

	private String title;

	private EmploymentShifts(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getCode() {
		return name();
	}
}
