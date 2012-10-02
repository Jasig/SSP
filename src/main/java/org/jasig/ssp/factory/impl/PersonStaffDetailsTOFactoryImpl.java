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

import org.jasig.ssp.dao.PersonStaffDetailsDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonStaffDetailsTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.PersonStaffDetailsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersonStaffDetails transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class PersonStaffDetailsTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonStaffDetailsTO, PersonStaffDetails>
		implements PersonStaffDetailsTOFactory {

	public PersonStaffDetailsTOFactoryImpl() {
		super(PersonStaffDetailsTO.class, PersonStaffDetails.class);
	}

	@Autowired
	private transient PersonStaffDetailsDao dao;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonStaffDetailsDao getDao() {
		return dao;
	}

	@Override
	public PersonStaffDetails from(final PersonStaffDetailsTO tObject)
			throws ObjectNotFoundException {

		if ((tObject.getId() == null) && (tObject.getPersonId() != null)) {
			final Person person = personService.get(tObject.getPersonId());
			final PersonStaffDetails unsetModel = person.getStaffDetails();
			if (unsetModel != null) {
				tObject.setId(unsetModel.getId());
			}
		}

		final PersonStaffDetails model = super.from(tObject);

		model.setOfficeLocation(tObject.getOfficeLocation());
		model.setOfficeHours(tObject.getOfficeHours());
		model.setDepartmentName(tObject.getDepartmentName());

		return model;
	}
}