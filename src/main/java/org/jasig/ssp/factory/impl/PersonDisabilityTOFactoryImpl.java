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
package org.jasig.ssp.factory.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.PersonDisabilityDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonDisabilityTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisability;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.transferobject.PersonDisabilityTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonDisabilityTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonDisabilityTO, PersonDisability>
		implements PersonDisabilityTOFactory {

	public PersonDisabilityTOFactoryImpl() {
		super(PersonDisabilityTO.class, PersonDisability.class);
	}

	@Autowired
	private transient PersonDisabilityDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient DisabilityStatusService disabilityStatusService;	
	
	@Override
	protected PersonDisabilityDao getDao() {
		return dao;
	}

	@Override
	public PersonDisability from(final PersonDisabilityTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			final Person person = personService.get(tObject.getPersonId());
			final PersonDisability unsetModel = person.getDisability();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonDisability model = super.from(tObject);

		model.setDisabilityStatus(tObject.getDisabilityStatusId() == null ? null :
			disabilityStatusService.get(tObject.getDisabilityStatusId()));
	
		model.setIntakeCounselor(tObject.getIntakeCounselor());
		model.setReferredBy(tObject.getReferredBy());
		model.setContactName(tObject.getContactName());
		model.setReleaseSigned(tObject.getReleaseSigned());
		model.setRecordsRequested(tObject.getRecordsRequested());
		model.setRecordsRequestedFrom(tObject.getRecordsRequestedFrom());
		model.setReferForScreening(tObject.getReferForScreening());
		model.setDocumentsRequestedFrom(tObject.getDocumentsRequestedFrom());
		model.setRightsAndDuties(tObject.getRightsAndDuties());
		model.setEligibleLetterSent(tObject.getEligibleLetterSent());
		model.setEligibleLetterDate(tObject.getEligibleLetterDate());
		model.setIneligibleLetterSent(tObject.getIneligibleLetterSent());
		model.setIneligibleLetterDate(tObject.getIneligibleLetterDate());
		model.setNoDocumentation(tObject.getNoDocumentation());
		model.setInadequateDocumentation(tObject.getInadequateDocumentation());
		model.setNoDisability(tObject.getNoDisability());
		model.setNoSpecialEd(tObject.getNoSpecialEd());
		model.setTempEligibilityDescription(tObject.getTempEligibilityDescription());
		model.setOnMedication(tObject.getOnMedication());
		model.setMedicationList(tObject.getMedicationList());
		model.setFunctionalLimitations(tObject.getFunctionalLimitations());
		
		return model;
	}
}