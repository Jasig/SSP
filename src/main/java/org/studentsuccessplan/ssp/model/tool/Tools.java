package edu.sinclair.ssp.model.tool;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import edu.sinclair.ssp.transferobject.jsonserializer.CodeAndProperty;
import edu.sinclair.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum Tools implements CodeAndProperty {
	INTAKE("Student Intake"), ACTION_PLAN("Action Plan");

	private String title;

	private Tools(String title) {
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
