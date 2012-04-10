package edu.sinclair.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.AbstractReference;
import edu.sinclair.ssp.util.SspStringUtils;

public class FormQuestionTO {

	private UUID id;
	private String label;
	private String type;
	private boolean readOnly;
	private boolean required;
	private String value;
	private List<String> values;
	private String maximumLength;
	private List<FormOptionTO> options;
	private String validationExpression;
	private String visibilityExpression;
	private String availabilityExpression;

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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(UUID value) {
		this.value = value.toString();
	}

	public void setValue(AbstractReference value) {
		this.value = value.getId().toString();
	}

	public void setValue(boolean value) {
		this.value = SspStringUtils.stringFromBoolean(value);
	}

	public void setValue(int value) {
		this.value = String.valueOf(value);
	}

	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public String getMaximumLength() {
		return maximumLength;
	}
	public void setMaximumLength(String maximumLength) {
		this.maximumLength = maximumLength;
	}
	public List<FormOptionTO> getOptions() {
		return options;
	}
	public void setOptions(List<FormOptionTO> options) {
		this.options = options;
	}
	public String getValidationExpression() {
		return validationExpression;
	}
	public void setValidationExpression(String validationExpression) {
		this.validationExpression = validationExpression;
	}
	public String getVisibilityExpression() {
		return visibilityExpression;
	}
	public void setVisibilityExpression(String visibilityExpression) {
		this.visibilityExpression = visibilityExpression;
	}
	public String getAvailabilityExpression() {
		return availabilityExpression;
	}
	public void setAvailabilityExpression(String availabilityExpression) {
		this.availabilityExpression = availabilityExpression;
	}

}
