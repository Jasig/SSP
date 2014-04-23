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

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutcomeTO;

public class EarlyAlertOutcomeMessageTemplateTO extends EarlyAlertOutcomeTO {

	private CoachPersonLiteMessageTemplateTO creator;
	
	public EarlyAlertOutcomeMessageTemplateTO() {
		// TODO Auto-generated constructor stub
	}

	public EarlyAlertOutcomeMessageTemplateTO(UUID id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	public EarlyAlertOutcomeMessageTemplateTO(UUID id, String name,
			String description, short sortOrder) {
		super(id, name, description, sortOrder);
		// TODO Auto-generated constructor stub
	}

	public EarlyAlertOutcomeMessageTemplateTO(EarlyAlertOutcome model, Person creator) {
		super(model);
		this.setCreator(new CoachPersonLiteMessageTemplateTO(creator));
	}

	public CoachPersonLiteMessageTemplateTO getCreator() {
		return creator;
	}

	public void setCreator(CoachPersonLiteMessageTemplateTO creator) {
		this.creator = creator;
	}

}
