package org.studentsuccessplan.ssp.model.reference;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import org.studentsuccessplan.ssp.transferobject.jsonserializer.CodeAndProperty;
import org.studentsuccessplan.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum Genders implements CodeAndProperty {
	M("Male"), F("Female");

	private String title;

	private Genders(String title) {
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
