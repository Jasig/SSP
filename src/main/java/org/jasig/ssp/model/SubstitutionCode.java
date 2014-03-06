package org.jasig.ssp.model;

public enum SubstitutionCode {
TERM("Different Term"), SUBSTITUTABLE_COURSE("Substituted Course");

private String displayText;

private SubstitutionCode(String displayText)
{
	this.setDisplayText(displayText);
}

public String getDisplayText() {
	return displayText;
}

public void setDisplayText(String displayText) {
	this.displayText = displayText;
}
}
