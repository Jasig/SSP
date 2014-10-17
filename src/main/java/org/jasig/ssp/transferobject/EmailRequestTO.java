package org.jasig.ssp.transferobject;

public class EmailRequestTO {

	private String emailSubject;
	private String emailBody;
	
	/** 
	 * non-hibernate transfer object used when velocity engine needed to render
	 * data without gaining access to a hibernate DAO
	 */
	public EmailRequestTO() {}
		
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	
	public String getEmailSubject() {
		return this.emailSubject;
	}
	
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	
	public String getEmailBody() {
		return this.emailBody;
	}
}
