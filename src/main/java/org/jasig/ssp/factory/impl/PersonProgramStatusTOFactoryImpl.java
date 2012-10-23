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

import java.util.Date;

import org.jasig.ssp.dao.PersonProgramStatusDao;
import org.jasig.ssp.factory.PersonProgramStatusTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusChangeReasonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonProgramStatus transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class PersonProgramStatusTOFactoryImpl
		extends
		AbstractPersonAssocReferenceTOFactory<PersonProgramStatusTO, PersonProgramStatus, ProgramStatus>
		implements PersonProgramStatusTOFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonServiceReasonTOFactoryImpl.class);

	public PersonProgramStatusTOFactoryImpl() {
		super(PersonProgramStatusTO.class, PersonProgramStatus.class);
	}

	@Autowired
	private transient PersonProgramStatusDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ProgramStatusService service;

	@Autowired
	private transient ProgramStatusChangeReasonService serviceProgramStatusChangeReasonService;

	@Override
	protected PersonProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public PersonProgramStatus from(
			final PersonProgramStatusTO tObject)
			throws ObjectNotFoundException {
		final PersonProgramStatus model = super.from(tObject);

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		model.setProgramStatus(tObject.getProgramStatusId() == null ? null
				: service.get(tObject.getProgramStatusId()));
		model.setProgramStatusChangeReason(tObject
				.getProgramStatusChangeReasonId() == null ? null
				: serviceProgramStatusChangeReasonService.get(tObject
						.getProgramStatusChangeReasonId()));

		model.setEffectiveDate(tObject.getEffectiveDate());
		model.setExpirationDate(tObject.getExpirationDate());

		return model;
	}

	@Override
	public PersonProgramStatus fromLite(
			final ReferenceLiteTO<ProgramStatus> lite,
			final Person person) throws ObjectNotFoundException {

		final PagingWrapper<PersonProgramStatus> results = dao
				.getAllForPersonIdAndProgramStatusId(person.getId(),
						lite.getId(), new SortingAndPaging(ObjectStatus.ACTIVE));

		if (results.getResults() > 1) {
			final StringBuilder msg = new StringBuilder(
					"Multiple active PersonProgramStatuss found for Person: ");
			msg.append(person.getId());
			msg.append(", ProgramStatus: ");
			msg.append(lite.getId());
			LOGGER.error(msg.toString());
			throw new ObjectNotFoundException(msg.toString(),
					"PersonProgramStatus");
		} else if (results.getResults() == 1) {
			return results.getRows().iterator().next();
		}

		// else
		final PersonProgramStatus pps = new PersonProgramStatus();
		pps.setPerson(person);
		pps.setProgramStatus(service.get(lite.getId()));
		pps.setEffectiveDate(new Date());
		return pps;
	}
}