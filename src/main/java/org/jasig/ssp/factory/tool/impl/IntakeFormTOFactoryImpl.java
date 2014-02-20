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
package org.jasig.ssp.factory.tool.impl;

import java.util.Set;

import org.jasig.ssp.factory.PersonChallengeTOFactory;
import org.jasig.ssp.factory.PersonCompletedItemTOFactory;
import org.jasig.ssp.factory.PersonDemographicsTOFactory;
import org.jasig.ssp.factory.PersonEducationGoalTOFactory;
import org.jasig.ssp.factory.PersonEducationLevelTOFactory;
import org.jasig.ssp.factory.PersonEducationPlanTOFactory;
import org.jasig.ssp.factory.PersonFundingSourceTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.tool.IntakeFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssoc;
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.jasig.ssp.util.SetOps;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class IntakeFormTOFactoryImpl implements IntakeFormTOFactory {

	@Autowired
	private transient PersonTOFactory personTOFactory;

	@Autowired
	private transient PersonDemographicsTOFactory personDemographicsTOFactory;

	@Autowired
	private transient PersonEducationGoalTOFactory personEducationGoalTOFactory;

	@Autowired
	private transient PersonEducationPlanTOFactory personEducationPlanTOFactory;

	@Autowired
	private transient PersonEducationLevelTOFactory personEducationLevelTOFactory;

	@Autowired
	private transient PersonFundingSourceTOFactory personFundingSourceTOFactory;

	@Autowired
	private transient PersonChallengeTOFactory personChallengeTOFactory;
	
	@Autowired
	private transient PersonCompletedItemTOFactory completedItemsTOFactory;

	@Override
	public IntakeFormTO from(final IntakeForm model) {
		return new IntakeFormTO(model);
	}

	@Override
	public IntakeForm from(final IntakeFormTO tObject)
			throws ObjectNotFoundException, ValidationException {
		final IntakeForm model = new IntakeForm();

		if (tObject.getPerson() != null) {
			model.setPerson(personTOFactory.from(tObject.getPerson()));
		} else {
			throw new ValidationException("Person is Required");
		}

		if (tObject.getPersonDemographics() != null) {
			// is there one already present?
			if ((model.getPerson() != null)
					&& (model.getPerson().getDemographics() != null)) {
				tObject.getPersonDemographics().setId(
						model.getPerson().getDemographics().getId());
			}

			model.getPerson()
					.setDemographics(
							personDemographicsTOFactory.from(tObject
									.getPersonDemographics()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getDemographics() != null)) {
				model.getPerson().getDemographics()
						.setObjectStatus(ObjectStatus.INACTIVE);
			}

		}

		if (tObject.getPersonEducationGoal() != null) {
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationGoal() != null)) {
				tObject.getPersonEducationGoal().setId(
						model.getPerson().getEducationGoal().getId());
			}

			model.getPerson().setEducationGoal(
					personEducationGoalTOFactory.from(tObject
							.getPersonEducationGoal()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationPlan() != null)) {
				model.getPerson().getEducationPlan()
						.setObjectStatus(ObjectStatus.INACTIVE);
			}
		}

		if (tObject.getPersonEducationPlan() != null) {
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationPlan() != null)) {
				tObject.getPersonEducationPlan().setId(
						model.getPerson().getEducationPlan().getId());
			}

			model.getPerson().setEducationPlan(
					personEducationPlanTOFactory.from(tObject
							.getPersonEducationPlan()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getEducationPlan() != null)) {
				model.getPerson().getEducationPlan()
						.setObjectStatus(ObjectStatus.INACTIVE);
			}
		}

		if ((tObject.getPersonEducationLevels() != null)
				&& !tObject.getPersonEducationLevels().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getEducationLevels(),
					personEducationLevelTOFactory.asSet(
							tObject.getPersonEducationLevels()));
		} else {
			// clearing education levels
			SetOps.softDeleteSetItems(model.getPerson().getEducationLevels());
		}

		associateWithPerson(model.getPerson().getEducationLevels(),
				model.getPerson());

		if ((tObject.getPersonFundingSources() != null)
				&& !tObject.getPersonFundingSources().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getFundingSources(),
					personFundingSourceTOFactory.asSet(
							tObject.getPersonFundingSources()));
		} else {
			// clearing fundingSources
			SetOps.softDeleteSetItems(model.getPerson().getFundingSources());
		}

		associateWithPerson(model.getPerson().getFundingSources(),
				model.getPerson());

		if ((tObject.getPersonChallenges() != null)
				&& !tObject.getPersonChallenges().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getChallenges(),
					personChallengeTOFactory.asSet(
							tObject.getPersonChallenges()));
		} else {
			SetOps.softDeleteSetItems(model.getPerson().getChallenges());
		}

		associateWithPerson(model.getPerson().getChallenges(),
				model.getPerson());
		
		if ((tObject.getPersonChecklist() != null)
				&& !tObject.getPersonChecklist().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getCompletedItems(),
					completedItemsTOFactory.asSet(
							tObject.getPersonChecklist()));
		} else {
			SetOps.softDeleteSetItems(model.getPerson().getCompletedItems());
		}

		associateWithPerson(model.getPerson().getCompletedItems(),
				model.getPerson());

		return model;
	}

	private <T extends PersonAssoc> void associateWithPerson(
			final Set<T> personAssocs, final Person person) {
		for (final PersonAssoc pa : personAssocs) {
			if (!(pa.getPerson().getId()).equals(person.getId())) {
				pa.setPerson(person);
			}
		}
	}
}
