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

import org.jasig.ssp.dao.PersonSpecialServiceGroupDao;
import org.jasig.ssp.factory.PersonSpecialServiceGroupTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.transferobject.PersonSpecialServiceGroupTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonSpecialServiceGroupTOFactoryImpl
		extends
		AbstractPersonAssocReferenceTOFactory<PersonSpecialServiceGroupTO, PersonSpecialServiceGroup, SpecialServiceGroup>
		implements PersonSpecialServiceGroupTOFactory {

	public PersonSpecialServiceGroupTOFactoryImpl() {
		super(PersonSpecialServiceGroupTO.class,
				PersonSpecialServiceGroup.class);
	}

	@Autowired
	private transient PersonSpecialServiceGroupDao dao;

	@Autowired
	private transient SpecialServiceGroupService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonSpecialServiceGroupDao getDao() {
		return dao;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSpecialServiceGroupTOFactoryImpl.class);

	@Override
	public PersonSpecialServiceGroup from(
			final PersonSpecialServiceGroupTO tObject)
			throws ObjectNotFoundException {
		final PersonSpecialServiceGroup model = super.from(tObject);

		model.setSpecialServiceGroup(tObject.getSpecialServiceGroupId() == null ? null
				: service.get(tObject.getSpecialServiceGroupId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}

	@Override
	public PersonSpecialServiceGroup fromLite(
			final ReferenceLiteTO<SpecialServiceGroup> lite,
			final Person person) throws ObjectNotFoundException {

		final PagingWrapper<PersonSpecialServiceGroup> results = dao
				.getAllForPersonIdAndSpecialServiceGroupId(person.getId(),
						lite.getId(), new SortingAndPaging(ObjectStatus.ACTIVE));

		if (results.getResults() > 1) {
			LOGGER.error("Multiple active PersonSpecialServiceGroups found for Person: "
					+ person.getId().toString()
					+ "SpecialServiceGroup:"
					+ lite.getId().toString());
			return results.getRows().iterator().next();
		} else if (results.getResults() == 1) {
			return results.getRows().iterator().next();
		}

		// else
		final PersonSpecialServiceGroup pssg = new PersonSpecialServiceGroup();
		pssg.setPerson(person);
		pssg.setSpecialServiceGroup(service.get(lite.getId()));
		return pssg;
	}
}