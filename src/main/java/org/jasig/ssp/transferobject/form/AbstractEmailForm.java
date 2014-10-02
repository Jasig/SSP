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
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.web.api.validation.ValidationException;
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

	public boolean hasEmailSubject() {
		return StringUtils.isNotBlank(emailSubject);
	}

	public boolean hasEmailBody() {
		return StringUtils.isNotBlank(emailBody);
	}
	
	public boolean hasValidDeliveryAddresses(){
		try {
			getValidDeliveryAddressesOrFail();
			return true;
		} catch ( ValidationException e ) {
			return false;
		}
	}

	public EmailAddress getValidDeliveryAddressesOrFail()
			throws ValidationException {
		
		String toAddr = null;
		Set<String> ccAddrs = Sets.newLinkedHashSet();
		final List<String> primaryAddrs = getValidAddressesFromString(getPrimaryEmail());
		toAddr = collectValidDeliveryAddresses(primaryAddrs, toAddr, ccAddrs);

		final List<String> secondaryAddrs = getValidAddressesFromString(getSecondaryEmail());
		toAddr = collectValidDeliveryAddresses(secondaryAddrs, toAddr, ccAddrs);

		final List<String> recipientAddrs = getValidAddressesFromList(getRecipientEmailAddresses());
		toAddr = collectValidDeliveryAddresses(recipientAddrs, toAddr, ccAddrs);

		// 'coachEmail' *does* makes sense as a 'to' addr when invoked from the Action Plan UI tool. That's
		// also why it's checked ahead of 'additional email'
		final List<String> coachAddrs = getValidAddressesFromString(getCoachEmail());
		toAddr = collectValidDeliveryAddresses(coachAddrs, toAddr, ccAddrs);

		final List<String> additionalAddrs = getValidAddressesFromString(getAdditionalEmail());
		toAddr = collectValidDeliveryAddresses(additionalAddrs, toAddr, ccAddrs);

		if ( StringUtils.isBlank(toAddr) ) {
			throw new ValidationException("Need at least one valid address to use as the 'to' delivery address.");
		}

		if ( ccAddrs.isEmpty() ) {
			return new EmailAddress(toAddr, null, null);
		}

		final StringBuilder ccBuilder = new StringBuilder();
		for ( String ccAddr : ccAddrs ) {
			ccBuilder.append(ccAddr.trim()).append(", ");
		}
		ccBuilder.setLength(ccBuilder.length() - 2);

		return new EmailAddress(toAddr, ccBuilder.toString(), null);
	}

	/**
	 * A little weird, but prevents a lot of code duplication in getValidDeliveryAddressesOrFail(). The return
	 * type is the 'to' address to use in {@code EmailAddress} typically being built by the caller. Will always
	 * return {@code currentTo} unless that arg is null, in which case it returns the firt valid address in
	 * {@code candidateAddrs}, if any.
	 * @param candidateAddrs
	 * @param currentTo
	 * @param currentCcAddrs
	 * @return
	 */
	private String collectValidDeliveryAddresses(List<String> candidateAddrs, String currentTo, Set<String> currentCcAddrs) {
		if ( candidateAddrs != null && !(candidateAddrs.isEmpty()) ) {
			if ( StringUtils.isBlank(currentTo) ) {
				final Pair<String,List<String>> popped = pop(candidateAddrs);
				if ( popped != null ) {
					currentTo = popped.getFirst().trim();
					currentCcAddrs.addAll(popped.getSecond());
				}
			} else {
				currentCcAddrs.addAll(candidateAddrs);
			}
		}
		return currentTo;
	}

	private Pair<String,List<String>> pop(List<String> strs) {
		if ( strs == null || strs.isEmpty() ) {
			return null;
		}
		return new Pair(strs.get(0), strs.size() == 1 ? Lists.newArrayListWithCapacity(0) : strs.subList(1,strs.size()));
	}
	
	private List<String> getValidAddressesFromString(String str){
		List<String> validatedAddresses = new ArrayList<String>();
		if(StringUtils.isBlank(str))
			return validatedAddresses;
		
		String[] addresses = str.split(",");
		for (String address : addresses) {
			if(isValidEmailAddress(address, useStrictValidation)){
				validatedAddresses.add(address.trim());
				continue;
			}
			getLogger().debug("Invalid address {} in address list {}", address, str);
		}
		return validatedAddresses;
	}

	private List<String> getValidAddressesFromList(List<String> strs) {
		if ( strs == null || strs.isEmpty() ) {
			return Lists.newArrayListWithCapacity(0);
		}
		final List<String> validatedAddresses = Lists.newArrayListWithExpectedSize(strs.size());
		for ( String str : strs ) {
			if ( str == null ) {
				continue;
			}
			str = str.trim();
			if ( isValidEmailAddress(str, useStrictValidation) ) {
				validatedAddresses.add(str);
			} else {
				getLogger().debug("Invalid address {} in address list {}", str, str);
			}
		}
		return validatedAddresses;
	}
	
	private static boolean isValidEmailAddress(String address, Boolean strict){
		if(StringUtils.isBlank(address))
			return false;
		if(address.length() > 254)
			return false;
		address = address.trim();
	    String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	    String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
	    String emailRegex = strict != null && strict ? stricterFilterString : laxString;
	    java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
	    java.util.regex.Matcher m = p.matcher(address);
	    return m.matches();
	}
	
	public static boolean hasValidEmailAddress(String str, Boolean useStrictValidation){
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

	protected Logger getLogger(){
		return LOGGER;
	}

}
