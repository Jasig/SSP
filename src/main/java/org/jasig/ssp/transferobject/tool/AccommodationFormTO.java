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
package org.jasig.ssp.transferobject.tool;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jasig.ssp.model.tool.AccommodationForm;
import org.jasig.ssp.transferobject.PersonDisabilityTO;
import org.jasig.ssp.transferobject.PersonDisabilityAgencyTO;
import org.jasig.ssp.transferobject.PersonDisabilityAccommodationTO;
import org.jasig.ssp.transferobject.PersonDisabilityTypeTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.TransferObject;

/**
 * Accommodation tool form transfer object
 */
public class AccommodationFormTO implements TransferObject<AccommodationForm> { // NOPMD

	@Valid
	private PersonTO person;

	@Valid
	private PersonDisabilityTO personDisability;

	@Valid
	private List<PersonDisabilityAgencyTO> personDisabilityAgencies;
	
	@Valid
	private List<PersonDisabilityAccommodationTO> personDisabilityAccommodations;

	@Valid
	private List<PersonDisabilityTypeTO> personDisabilityTypes;	
	
	private Map<String, Object> referenceData;

	public AccommodationFormTO() {
		super();
	}

	public AccommodationFormTO(final AccommodationForm model) {
		super();
		from(model);
	}

	@Override
	public final void from(final AccommodationForm model) { // NOPMD
		if (model.getPerson() != null) {
			person = new PersonTO(model.getPerson());
		}

		if (model.getPerson().getDisability() != null) {
			personDisability = new PersonDisabilityTO(model.getPerson()
					.getDisability());
			if (personDisability.getPersonId() == null) {
				personDisability.setPersonId(person.getId());
			}
		}

		if ((model.getPerson().getDisabilityAgencies() != null)
				&& !(model.getPerson().getDisabilityAgencies().isEmpty())) {
			personDisabilityAgencies = PersonDisabilityAgencyTO.toTOList(model
					.getPerson().getDisabilityAgencies());
		}
		
		if ((model.getPerson().getDisabilityAccommodations() != null)
				&& !(model.getPerson().getDisabilityAccommodations().isEmpty())) {
			personDisabilityAccommodations = PersonDisabilityAccommodationTO.toTOList(model
					.getPerson().getDisabilityAccommodations());
		}
		
		if ((model.getPerson().getDisabilityTypes() != null)
				&& !(model.getPerson().getDisabilityTypes().isEmpty())) {
			personDisabilityTypes = PersonDisabilityTypeTO.toTOList(model
					.getPerson().getDisabilityTypes());
		}
	}

	public PersonTO getPerson() {
		return person;
	}

	public void setPerson(final PersonTO person) {
		this.person = person;
	}

	public PersonDisabilityTO getPersonDisability() {
		return personDisability;
	}

	public void setPersonDisability(
			final PersonDisabilityTO personDisability) {
		this.personDisability = personDisability;
	}

	public List<PersonDisabilityAgencyTO> getPersonDisabilityAgencies() {
		return personDisabilityAgencies;
	}

	public void setPersonDisabilityAgencies(
			final List<PersonDisabilityAgencyTO> personDisabilityAgencies) {
		this.personDisabilityAgencies = personDisabilityAgencies;
	}

	public List<PersonDisabilityAccommodationTO> getPersonDisabilityAccommodations() {
		return personDisabilityAccommodations;
	}

	public void setPersonDisabilityAccommodations(
			final List<PersonDisabilityAccommodationTO> personDisabilityAccommodations) {
		this.personDisabilityAccommodations = personDisabilityAccommodations;
	}	

	public List<PersonDisabilityTypeTO> getPersonDisabilityTypes() {
		return personDisabilityTypes;
	}	
	
	public void setPersonDisabilityTypes(
			final List<PersonDisabilityTypeTO> personDisabilityTypes) {
		this.personDisabilityTypes = personDisabilityTypes;
	}	
	
	public Map<String, Object> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(final Map<String, Object> referenceData) {
		this.referenceData = referenceData;
	}
}