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
import org.jasig.ssp.dao.PersonDisabilityAgencyDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonDisabilityAgencyTOFactory;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.DisabilityAgencyService;
import org.jasig.ssp.transferobject.PersonDisabilityAgencyTO;

@Service
@Transactional(readOnly = true)
public class PersonDisabilityAgencyTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonDisabilityAgencyTO, PersonDisabilityAgency>
		implements PersonDisabilityAgencyTOFactory {

	public PersonDisabilityAgencyTOFactoryImpl() {
		super(PersonDisabilityAgencyTO.class, PersonDisabilityAgency.class);
	}

	@Autowired
	private transient PersonDisabilityAgencyDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient DisabilityAgencyService disabilityAgencyService;

	@Override
	protected PersonDisabilityAgencyDao getDao() {
		return dao;
	}

	@Override
	public PersonDisabilityAgency from(final PersonDisabilityAgencyTO tObject)
			throws ObjectNotFoundException {
		final PersonDisabilityAgency model = super.from(tObject);

		model.setDescription(tObject.getDescription());

		model.setDisabilityAgency((tObject.getDisabilityAgencyId() == null) ? null :
				disabilityAgencyService.get(tObject.getDisabilityAgencyId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}