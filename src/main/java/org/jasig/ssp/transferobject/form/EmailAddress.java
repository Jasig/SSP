package org.jasig.ssp.transferobject.form;

public class EmailAddress {

	private String to;
	private String cc;
	private String bcc;
	
	
	public EmailAddress(String to, String cc, String bcc) {
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
	}
	
	public EmailAddress() {
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getCc() {
		return cc;
	}


	public void setCc(String cc) {
		this.cc = cc;
	}


	public String getBcc() {
		return bcc;
	}


	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

}
