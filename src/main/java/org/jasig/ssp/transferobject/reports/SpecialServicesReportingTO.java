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
package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.web.api.reports.SpecialServicesReportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SpecialServicesReportingTO transfer object
 */
public class SpecialServicesReportingTO implements Serializable {

	private static final long serialVersionUID = -5316251830392732984L;

	@SuppressWarnings("unused")
	private SpecialServicesReportingTO() {
		// must create through ReportablePerson
	}

	public SpecialServicesReportingTO(final Person person) {
		super();
		this.person = person;
	}

	// TODO: Should be a PersonTO, but SpecialServices not populated by service
	// in the TO
	private Person person;

	public String getFirstName() {
		return person.getFirstName();
	}

	public String getLastName() {
		return person.getLastName();
	}

	public String getMiddleName() {
		return person.getMiddleName();
	}

	public String getUserId() {
		return person.getUsername();
	}

	public String getStudentType() {
		if (person.getStudentType() == null) {
			return null;
		}
		else {
			return person.getStudentType().getName();
		}
	}

	protected Logger getLogger() {
		return LOGGER;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServicesReportController.class);

	public List<String> getSpecialServices() {
		final List<String> retVal = new ArrayList<String>();
		final Iterator<PersonSpecialServiceGroup> pssIter = person
				.getSpecialServiceGroups().iterator();
		while (pssIter.hasNext()) {
			PersonSpecialServiceGroup pssg = pssIter.next();
			if(pssg.getObjectStatus().compareTo(ObjectStatus.ACTIVE) == 0){
				retVal.add(pssg.getSpecialServiceGroup().getName());
			}
		}
		return retVal;
	}

}
