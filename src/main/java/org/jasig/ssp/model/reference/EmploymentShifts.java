package org.jasig.ssp.model.reference;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndProperty;
import org.jasig.ssp.transferobject.jsonserializer.CodeAndPropertySerializer;

@JsonSerialize(using = CodeAndPropertySerializer.class)
public enum EmploymentShifts implements CodeAndProperty {
	FIRST("1st"), SECOND("2nd"), THIRD("3rd"), NOT_APPLICABLE("Not Applicable");

	public static EmploymentShifts getEnumByValue(String value) {
		if ("1st".equals(value)) {
			return FIRST;
		} else if ("2nd".equals(value)) {
			return SECOND;
		} else if ("3rd".equals(value)) {
			return THIRD;
		} else if ("Not Applicable".equals(value)) {
			return NOT_APPLICABLE;
		}

		return null;
	}

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
