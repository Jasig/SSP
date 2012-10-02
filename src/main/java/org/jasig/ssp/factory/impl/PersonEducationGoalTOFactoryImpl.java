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

import org.jasig.ssp.dao.PersonEducationGoalDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonEducationGoalTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.EducationGoalService;
import org.jasig.ssp.transferobject.PersonEducationGoalTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonEducationGoal transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class PersonEducationGoalTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonEducationGoalTO, PersonEducationGoal>
		implements PersonEducationGoalTOFactory {

	public PersonEducationGoalTOFactoryImpl() {
		super(PersonEducationGoalTO.class, PersonEducationGoal.class);
	}

	@Autowired
	private transient PersonEducationGoalDao dao;

	@Autowired
	private transient EducationGoalService educationGoalService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonEducationGoalDao getDao() {
		return dao;
	}

	@Override
	public PersonEducationGoal from(final PersonEducationGoalTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			final Person person = personService.get(tObject.getPersonId());
			final PersonEducationGoal unsetModel = person.getEducationGoal();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonEducationGoal model = super.from(tObject);

		model.setHowSureAboutMajor(tObject.getHowSureAboutMajor());
		model.setDescription(tObject.getDescription());
		model.setPlannedOccupation(tObject.getPlannedOccupation());
		model.setPlannedMajor(tObject.getPlannedMajor());
		model.setCareerDecided(tObject.getCareerDecided());
		model.setHowSureAboutOccupation(tObject.getHowSureAboutOccupation());
		model.setConfidentInAbilities(tObject.getConfidentInAbilities());
		model.setAdditionalAcademicProgramInformationNeeded(tObject.getAdditionalAcademicProgramInformationNeeded());
		
		model.setEducationGoal(tObject.getEducationGoalId() == null ? null
				: educationGoalService.get(tObject.getEducationGoalId()));
		
		return model;
	}
}
