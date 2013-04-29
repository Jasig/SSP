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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.Plan;

/**
 * TO for /summary API call.  In early phases of development this will not hold a lot of meaningful data.
 * 
 * 
 */
public class PlanLiteTO extends AbstractPlanTO<Plan> {

	private String personId;
	
	/**
	 * Empty constructor.
	 */
	public PlanLiteTO() {
		super();
	}
	/**
	 * Empty constructor.
	 */
	public PlanLiteTO(Plan model) {
		super();
		from(model);
	}	

	@Override
	public void from(Plan model) {
		super.from(model);
		this.setPersonId(model.getPerson().getId().toString());
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}

}