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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.PersonDisabilityAccommodationDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonDisabilityAccommodationTOFactory;
import org.jasig.ssp.model.PersonDisabilityAccommodation;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.DisabilityAccommodationService;
import org.jasig.ssp.transferobject.PersonDisabilityAccommodationTO;

@Service
@Transactional(readOnly = true)
public class PersonDisabilityAccommodationTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonDisabilityAccommodationTO, PersonDisabilityAccommodation>
		implements PersonDisabilityAccommodationTOFactory {

	public PersonDisabilityAccommodationTOFactoryImpl() {
		super(PersonDisabilityAccommodationTO.class, PersonDisabilityAccommodation.class);
	}

	@Autowired
	private transient PersonDisabilityAccommodationDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient DisabilityAccommodationService disabilityAccommodationService;

	@Override
	protected PersonDisabilityAccommodationDao getDao() {
		return dao;
	}

	@Override
	public PersonDisabilityAccommodation from(final PersonDisabilityAccommodationTO tObject)
			throws ObjectNotFoundException {
		final PersonDisabilityAccommodation model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		model.setDisabilityAccommodation((tObject.getDisabilityAccommodationId() == null) ? null :
				disabilityAccommodationService.get(tObject.getDisabilityAccommodationId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}