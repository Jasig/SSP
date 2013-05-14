package org.jasig.ssp.transferobject;

/** PlanOutputTO is strictly for bringing back data from client
 * for printing and email purposes
 * 
 * @author jamesstanley
 *
 */
public class TemplateOutputTO extends AbstractPlanOutputTO {
	private TemplateTO template;
	private String outputFormat;
    private Boolean includeCourseDescription;
    private Boolean includeHeaderFooter;
    private Boolean includeTotalTimeExpected;
    private Boolean includeFinancialAidInformation;             
    private String emailTo;
    private String emailCC;
    private String notes;
    
    
    public TemplateOutputTO(){
    	super();
    }
    
	/**
	 * @param template
	 * @param form
	 * @param includeCourseDescription
	 * @param includeHeaderFooter
	 * @param includeTotalTimeExpected
	 * @param includeFinancialAidInformation
	 * @param emailTo
	 * @param emailCC
	 * @param notes
	 */
	public TemplateOutputTO(TemplateTO template, String outputFormat,
			Boolean includeCourseDescription, Boolean includeHeaderFooter,
			Boolean includeTotalTimeExpected,
			Boolean includeFinancialAidInformation, String emailTo,
			String emailCC, String notes) {
		super();
		this.template = template;
		this.outputFormat = outputFormat;
		this.includeCourseDescription = includeCourseDescription;
		this.includeHeaderFooter = includeHeaderFooter;
		this.includeTotalTimeExpected = includeTotalTimeExpected;
		this.includeFinancialAidInformation = includeFinancialAidInformation;
		this.emailTo = emailTo;
		this.emailCC = emailCC;
		this.notes = notes;
	}
	/**
	 * @return the template
	 */
	public TemplateTO getTemplate() {
		return template;
	}
	/**
	 * @param template the template to set
	 */
	public void setPlan(TemplateTO template) {
		this.template = template;
	}
	/**
	 * @return the form
	 */
	public String getOutputFormat() {
		return outputFormat;
	}
	/**
	 * @param form the form to set
	 */
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	/**
	 * @return the includeCourseDescription
	 */
	public Boolean getIncludeCourseDescription() {
		return includeCourseDescription;
	}
	/**
	 * @param includeCourseDescription the includeCourseDescription to set
	 */
	public void setIncludeCourseDescription(Boolean includeCourseDescription) {
		this.includeCourseDescription = includeCourseDescription;
	}
	/**
	 * @return the includeHeaderFooter
	 */
	public Boolean getIncludeHeaderFooter() {
		return includeHeaderFooter;
	}
	/**
	 * @param includeHeaderFooter the includeHeaderFooter to set
	 */
	public void setIncludeHeaderFooter(Boolean includeHeaderFooter) {
		this.includeHeaderFooter = includeHeaderFooter;
	}
	/**
	 * @return the includeTotalTimeExpected
	 */
	public Boolean getIncludeTotalTimeExpected() {
		return includeTotalTimeExpected;
	}
	/**
	 * @param includeTotalTimeExpected the includeTotalTimeExpected to set
	 */
	public void setIncludeTotalTimeExpected(Boolean includeTotalTimeExpected) {
		this.includeTotalTimeExpected = includeTotalTimeExpected;
	}
	/**
	 * @return the includeFinancialAidInformation
	 */
	public Boolean getIncludeFinancialAidInformation() {
		return includeFinancialAidInformation;
	}
	/**
	 * @param includeFinancialAidInformation the includeFinancialAidInformation to set
	 */
	public void setIncludeFinancialAidInformation(
			Boolean includeFinancialAidInformation) {
		this.includeFinancialAidInformation = includeFinancialAidInformation;
	}
	/**
	 * @return the emailTo
	 */
	public String getEmailTo() {
		return emailTo;
	}
	/**
	 * @param emailTo the emailTo to set
	 */
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	/**
	 * @return the emailCC
	 */
	public String getEmailCC() {
		return emailCC;
	}
	/**
	 * @param emailCC the emailCC to set
	 */
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
    


}
