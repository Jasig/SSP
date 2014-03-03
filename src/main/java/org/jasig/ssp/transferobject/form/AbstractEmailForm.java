package org.jasig.ssp.transferobject.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEmailForm {

	
	private Boolean sendToPrimaryEmail = false;
	
	private String primaryEmail;
	
	private Boolean sendToSecondaryEmail = false;
	
	private String secondaryEmail;
	
	private Boolean sendToAdditionalEmail = false;
	
	private String additionalEmail;
	
	private Boolean sendToCoachEmail = false;
	
	private String coachEmail;
	
	private String emailSubject;
	
	private String emailBody;
	
	private Boolean useStrictValidation = true;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractEmailForm.class);

	
	public Boolean getSendToPrimaryEmail() {
		return sendToPrimaryEmail;
	}

	public void setSendToPrimaryEmail(Boolean sendToPrimaryEmail) {
		this.sendToPrimaryEmail = sendToPrimaryEmail;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public Boolean getSendToSecondaryEmail() {
		return sendToSecondaryEmail;
	}

	public void setSendToSecondaryEmail(Boolean sendToSecondaryEmail) {
		this.sendToSecondaryEmail = sendToSecondaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public Boolean getSendToAdditionalEmail() {
		return sendToAdditionalEmail;
	}

	public void setSendToAdditionalEmail(Boolean sendToAdditionalEmail) {
		this.sendToAdditionalEmail = sendToAdditionalEmail;
	}

	public String getAdditionalEmail() {
		return additionalEmail;
	}

	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}

	public Boolean getSendToCoachEmail() {
		return sendToCoachEmail;
	}

	public void setSendToCoachEmail(Boolean sendToCoachEmail) {
		this.sendToCoachEmail = sendToCoachEmail;
	}

	public String getCoachEmail() {
		return coachEmail;
	}

	public void setCoachEmail(String coachEmail) {
		this.coachEmail = coachEmail;
	}
	
	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	protected Logger getLogger(){
		return LOGGER;
	}
	
	public Boolean hasEmailSubject()
	{
		return StringUtils.isNotBlank(emailSubject);
	}
	public Boolean hasEmailBody()
	{
		return StringUtils.isNotBlank(emailBody);
	}
	
	public Boolean  hasValidPrimaryAddress(){
		if(getSendToPrimaryEmail() && getAddressesFromString(getPrimaryEmail()).size() > 0)
				return true;
		
		if(getSendToSecondaryEmail() && getAddressesFromString(getSecondaryEmail()).size() > 0)
			return true;
		
		if(getSendToCoachEmail() && getAddressesFromString(getCoachEmail()).size() > 0)
			return true;
		
		if(getSendToAdditionalEmail() && getAddressesFromString(getAdditionalEmail()).size() > 0)
			return true;

		return false;
	}
	
	
	public  EmailAddress getValidEmailAddresses()
			throws IllegalArgumentException {
		
		String toAddress = null;
		
		StringBuilder ccBuilder = new StringBuilder("");
		
		List<String> addressSet = new ArrayList<String>();
		
		if(getSendToPrimaryEmail()) addressSet.addAll(getAddressesFromString(getPrimaryEmail()));
		if(getSendToSecondaryEmail()) addressSet.addAll(getAddressesFromString(getSecondaryEmail()));
		if(getSendToCoachEmail()) addressSet.addAll(getAddressesFromString(getCoachEmail()));
		if(getSendToAdditionalEmail())addressSet.addAll(getAddressesFromString(getAdditionalEmail()));
		
		if(addressSet.size() == 0){
			throw new IllegalArgumentException("Must enter at least one email address");
		}
		String prefix = "";
		for (String address : addressSet) {
			if(org.apache.commons.lang.StringUtils.isBlank(toAddress)){
				toAddress = address.trim();
				continue;
			}
			ccBuilder.append(prefix);
			ccBuilder.append(address.trim());
			prefix = ", ";			
		}
		if(org.apache.commons.lang.StringUtils.isBlank(toAddress)){
			throw new IllegalArgumentException("No valid email address found please enter at least one valid email address");
		}
		
		return new EmailAddress(toAddress, ccBuilder.toString().trim(), null);
	}
	
	private List<String> getAddressesFromString(String str){
		List<String> validatedAddresses = new ArrayList<String>();
		if(StringUtils.isBlank(str))
			return validatedAddresses;
		
		String[] addresses = str.split(",");
		for (String address : addresses) {
			if(isValidEmailAddress(address, useStrictValidation)){
				validatedAddresses.add(address.trim());
				continue;
			}
			getLogger().error("Sending task email, address not valid: " + address);
		}
		return validatedAddresses;
	}
	
	private static Boolean isValidEmailAddress(String address, Boolean strict){
		if(StringUtils.isBlank(address))
			return false;
		if(address.length() > 254)
			return false;
		address = address.trim();
	    String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	    String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
	    String emailRegex = strict ? stricterFilterString : laxString;
	    java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
	    java.util.regex.Matcher m = p.matcher(address);
	    return m.matches();
	}
	
	public static Boolean hasValidEmailAddress(String str, Boolean useStrictValidation){
		if(StringUtils.isBlank(str))
			return false;
		String[] addresses = str.split(",");
		for (String address : addresses) {
			if(isValidEmailAddress(address, useStrictValidation)){
				return true;
			}
			
		}
		return false;
	}

	public Boolean getUseStrictValidation() {
		return useStrictValidation;
	}

	public void setUseStrictValidation(Boolean useStrictValidation) {
		this.useStrictValidation = useStrictValidation;
	}

}
