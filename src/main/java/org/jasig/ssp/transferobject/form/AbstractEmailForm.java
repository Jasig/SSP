/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEmailForm {

		
	private String primaryEmail;
		
	private String secondaryEmail;
		
	private String additionalEmail;
	
	private List<String> recipientEmailAddresses;
	
	private String coachEmail;
	
	private String emailSubject;
	
	private String emailBody;
	
	private Boolean useStrictValidation = true;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractEmailForm.class);

	
	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}


	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getAdditionalEmail() {
		return additionalEmail;
	}

	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}

	public List<String> getRecipientEmailAddresses() {
		return recipientEmailAddresses;
	}

	public void setRecipientEmailAddresses(List<String> recipientEmailAddresses) {
		this.recipientEmailAddresses = recipientEmailAddresses;
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
		if(getAddressesFromString(getPrimaryEmail()).size() > 0)
				return true;
		if(getRecipientEmailAddresses() != null && getRecipientEmailAddresses().size() > 0)
			return true;
		
		if(getAddressesFromString(getSecondaryEmail()).size() > 0)
			return true;
		
		if(getAddressesFromString(getCoachEmail()).size() > 0)
			return true;
		
		if(getAddressesFromString(getAdditionalEmail()).size() > 0)
			return true;

		return false;
	}
	
	//TODO Current how to, cc and bcc are set is not very determinate, always only one to set throught this form
	public  EmailAddress getValidEmailAddresses()
			throws IllegalArgumentException {
		
		String toAddress = null;
		
		StringBuilder ccBuilder = new StringBuilder("");
		
		List<String> addressSet = new ArrayList<String>();		
	    
		addressSet.addAll(getAddressesFromString(getPrimaryEmail()));
		addressSet.addAll(getAddressesFromString(getSecondaryEmail()));
		if(getRecipientEmailAddresses() != null)
			addressSet.addAll(getRecipientEmailAddresses());
		addressSet.addAll(getAddressesFromString(getCoachEmail()));
		addressSet.addAll(getAddressesFromString(getAdditionalEmail()));
		
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
