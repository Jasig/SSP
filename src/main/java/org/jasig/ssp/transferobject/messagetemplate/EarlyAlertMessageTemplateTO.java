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
package org.jasig.ssp.transferobject.messagetemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.EarlyAlertTO;

public class EarlyAlertMessageTemplateTO extends EarlyAlertTO {


	private static final long serialVersionUID = 1432740352046594692L;
	
	private CoachPersonLiteMessageTemplateTO person;
	
	private CoachPersonLiteMessageTemplateTO coach;
	
	private CoachPersonLiteMessageTemplateTO creator;
	
	private Set<String> watcherEmails = new HashSet<String>();
	
	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert, Person creator) {
		super(earlyAlert);
		if(creator != null)
			this.creator = new CoachPersonLiteMessageTemplateTO(creator);
	}

	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert) {
		super(earlyAlert);
	}
	
	public EarlyAlertMessageTemplateTO(EarlyAlert earlyAlert, Person creator2,
			List<String> watcherEmailAddresses) {
		super(earlyAlert);
		if(creator != null)
			this.creator = new CoachPersonLiteMessageTemplateTO(creator2);
		
		if(watcherEmailAddresses != null)
			getWatcherEmails().addAll(watcherEmailAddresses);
	}

	public CoachPersonLiteMessageTemplateTO getPerson() {
		return person;
	}

	public void setPerson(CoachPersonLiteMessageTemplateTO person) {
		this.person = person;
	}
	
	public CoachPersonLiteMessageTemplateTO getCoach() {
		return coach;
	}

	public void setCoach(CoachPersonLiteMessageTemplateTO coach) {
		this.coach = coach;
	}

	public CoachPersonLiteMessageTemplateTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteMessageTemplateTO creator) {
		this.creator = creator;
	}

	@Override
	public final void from(EarlyAlert model){
		super.from(model);
		person = new CoachPersonLiteMessageTemplateTO(model.getPerson());
		if(model.getPerson().getCoach() != null){
			coach = new CoachPersonLiteMessageTemplateTO(model.getPerson().getCoach());
		}
	}

	public Set<String> getWatcherEmails() {
		return watcherEmails;
	}

	public void setWatcherEmails(Set<String> watcherEmails) {
		this.watcherEmails = watcherEmails;
	}

}
