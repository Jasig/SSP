package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.AbstractPlan;

public abstract class AbstractPlanOutputTO<P extends AbstractPlan,T extends AbstractPlanTO<P>> {
	
	private T plan;
	private String outputFormat;
    private Boolean includeCourseDescription;
    private Boolean includeHeaderFooter;
    private Boolean includeTotalTimeExpected;
    private Boolean includeFinancialAidInformation;             
    private String emailTo;
    private String emailCC;
    private String notes;
    
    public T getNonOutputTO() {
    	return plan;
    }
    
    public void setNonOuputTO(T plan) {
    	this.plan = plan;
    }
    
	public String getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	public Boolean getIncludeCourseDescription() {
		return includeCourseDescription;
	}
	public void setIncludeCourseDescription(Boolean includeCourseDescription) {
		this.includeCourseDescription = includeCourseDescription;
	}
	public Boolean getIncludeHeaderFooter() {
		return includeHeaderFooter;
	}
	public void setIncludeHeaderFooter(Boolean includeHeaderFooter) {
		this.includeHeaderFooter = includeHeaderFooter;
	}
	public Boolean getIncludeTotalTimeExpected() {
		return includeTotalTimeExpected;
	}
	public void setIncludeTotalTimeExpected(Boolean includeTotalTimeExpected) {
		this.includeTotalTimeExpected = includeTotalTimeExpected;
	}
	public Boolean getIncludeFinancialAidInformation() {
		return includeFinancialAidInformation;
	}
	public void setIncludeFinancialAidInformation(
			Boolean includeFinancialAidInformation) {
		this.includeFinancialAidInformation = includeFinancialAidInformation;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getEmailCC() {
		return emailCC;
	}
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
