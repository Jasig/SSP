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
import org.jasig.ssp.dao.PersonDemographicsDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonDemographicsTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.transferobject.PersonDemographicsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonDemographicsTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonDemographicsTO, PersonDemographics>
		implements PersonDemographicsTOFactory {

	public PersonDemographicsTOFactoryImpl() {
		super(PersonDemographicsTO.class, PersonDemographics.class);
	}

	@Autowired
	private transient PersonDemographicsDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient MaritalStatusService maritalStatusService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Autowired
	private transient CitizenshipService citizenshipService;

	@Autowired
	private transient ChildCareArrangementService childCareArrangementService;

	@Autowired
	private transient VeteranStatusService veteranStatusService;

	@Override
	protected PersonDemographicsDao getDao() {
		return dao;
	}

	@Override
	public PersonDemographics from(final PersonDemographicsTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			final Person person = personService.get(tObject.getPersonId());
			final PersonDemographics unsetModel = person.getDemographics();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonDemographics model = super.from(tObject);

		model.setMaritalStatus(tObject.getMaritalStatusId() == null ? null :
				maritalStatusService.get(tObject.getMaritalStatusId()));

		model.setEthnicity(tObject.getEthnicityId() == null ? null :
				ethnicityService.get(tObject.getEthnicityId()));

		model.setCitizenship(tObject.getCitizenshipId() == null ? null :
				citizenshipService.get(tObject.getCitizenshipId()));

		model.setVeteranStatus(tObject.getVeteranStatusId() == null ? null :
				veteranStatusService.get(tObject.getVeteranStatusId()));

		model.setChildCareArrangement(tObject.getChildCareArrangementId() == null ? null
				: childCareArrangementService.get(tObject
						.getChildCareArrangementId()));

		model.setGender(StringUtils.isEmpty(tObject.getGender()) ? null :
				Genders.valueOf(tObject.getGender()));

		model.setShift(StringUtils.isEmpty(tObject.getShift()) ? null :
				EmploymentShifts.valueOf(tObject.getShift()));

		model.setLocal(tObject.getLocal());
		model.setPrimaryCaregiver(tObject.getPrimaryCaregiver());
		model.setChildCareNeeded(tObject.getChildCareNeeded());
		model.setEmployed(tObject.getEmployed());
		model.setNumberOfChildren(tObject.getNumberOfChildren());
		model.setBalanceOwed(tObject.getBalanceOwed());
		model.setCountryOfResidence(tObject.getCountryOfResidence());
		model.setPaymentStatus(tObject.getPaymentStatus());
		model.setCountryOfCitizenship(tObject.getCountryOfCitizenship());
		model.setChildAges(tObject.getChildAges());
		model.setPlaceOfEmployment(tObject.getPlaceOfEmployment());
		model.setWage(tObject.getWage());
		model.setTotalHoursWorkedPerWeek(tObject.getTotalHoursWorkedPerWeek());

		return model;
	}
}
