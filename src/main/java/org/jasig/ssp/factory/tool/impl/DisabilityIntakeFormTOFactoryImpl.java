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

import org.jasig.ssp.factory.PersonDisabilityTOFactory;
import org.jasig.ssp.factory.PersonDisabilityAgencyTOFactory;
import org.jasig.ssp.factory.PersonDisabilityTypeTOFactory;
import org.jasig.ssp.factory.PersonDisabilityAccommodationTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.tool.DisabilityIntakeFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssoc;
import org.jasig.ssp.model.tool.DisabilityIntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.tool.DisabilityIntakeFormTO;
import org.jasig.ssp.util.SetOps;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DisabilityIntakeFormTOFactoryImpl implements DisabilityIntakeFormTOFactory {

	@Autowired
	private transient PersonTOFactory personTOFactory;

	@Autowired
	private transient PersonDisabilityTOFactory personDisabilityTOFactory;

	@Autowired
	private transient PersonDisabilityAgencyTOFactory personDisabilityAgencyTOFactory;

	@Autowired
	private transient PersonDisabilityTypeTOFactory personDisabilityTypeTOFactory;

	@Autowired
	private transient PersonDisabilityAccommodationTOFactory personDisabilityAccommodationTOFactory;

	@Override
	public DisabilityIntakeFormTO from(final DisabilityIntakeForm model) {
		return new DisabilityIntakeFormTO(model);
	}

	@Override
	public DisabilityIntakeForm from(final DisabilityIntakeFormTO tObject)
			throws ObjectNotFoundException, ValidationException {
		final DisabilityIntakeForm model = new DisabilityIntakeForm();

		if (tObject.getPerson() != null) {
			model.setPerson(personTOFactory.from(tObject.getPerson()));
		} else {
			throw new ValidationException("Person is Required");
		}

		if (tObject.getPersonDisability() != null) {
			// is there one already present?
			if ((model.getPerson() != null)
					&& (model.getPerson().getDisability() != null)) {
				tObject.getPersonDisability().setId(
						model.getPerson().getDisability().getId());
			}

			model.getPerson()
					.setDisability(
							personDisabilityTOFactory.from(tObject
									.getPersonDisability()));
		} else {
			// soft delete it if it exists
			if ((model.getPerson() != null)
					&& (model.getPerson().getDisability() != null)) {
				model.getPerson().getDisability()
						.setObjectStatus(ObjectStatus.INACTIVE);
			}

		}

		if ((tObject.getPersonDisabilityAgencies() != null)
				&& !tObject.getPersonDisabilityAgencies().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getDisabilityAgencies(),
					personDisabilityAgencyTOFactory.asSet(
							tObject.getPersonDisabilityAgencies()));
		} else {
			// clearing disabilityAgencies
			SetOps.softDeleteSetItems(model.getPerson().getDisabilityAgencies());
		}

		if ((tObject.getPersonDisabilityTypes() != null)
				&& !tObject.getPersonDisabilityTypes().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getDisabilityTypes(),
					personDisabilityTypeTOFactory.asSet(
							tObject.getPersonDisabilityTypes()));
		} else {
			// clearing disabilityTypes
			SetOps.softDeleteSetItems(model.getPerson().getDisabilityTypes());
		}		
	
		if ((tObject.getPersonDisabilityAccommodations() != null)
				&& !tObject.getPersonDisabilityAccommodations().isEmpty()) {
			SetOps.updateSet(
					model.getPerson().getDisabilityAccommodations(),
					personDisabilityAccommodationTOFactory.asSet(
							tObject.getPersonDisabilityAccommodations()));
		} else {
			// clearing disabilityAccommdations
			SetOps.softDeleteSetItems(model.getPerson().getDisabilityAccommodations());
		}		
		
		associateWithPerson(model.getPerson().getDisabilityAgencies(),
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