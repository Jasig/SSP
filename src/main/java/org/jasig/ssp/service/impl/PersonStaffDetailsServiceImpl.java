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
package org.jasig.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.PersonStaffDetailsDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonStaffDetailsService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonStaffDetails service implementation
 * 
 * @author jon.adams
 */
@Service
public class PersonStaffDetailsServiceImpl implements PersonStaffDetailsService {

	@Autowired
	private transient PersonStaffDetailsDao dao;

	@Override
	public PagingWrapper<PersonStaffDetails> getAll(final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonStaffDetails get(final UUID id) throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonStaffDetails forPerson(final Person person) {
		return person.getStaffDetails();
	}

	@Override
	public PersonStaffDetails create(final PersonStaffDetails obj) {
		return dao.save(obj);
	}

	@Override
	public PersonStaffDetails save(final PersonStaffDetails obj)
			throws ObjectNotFoundException {
		final PersonStaffDetails current = get(obj.getId());

		if (obj.getObjectStatus() != null) {
			current.setObjectStatus(obj.getObjectStatus());
		}

		current.setOfficeLocation(obj.getOfficeLocation());
		current.setOfficeHours(obj.getOfficeHours());
		current.setDepartmentName(obj.getDepartmentName());

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonStaffDetails current = get(id);

		if (null != current
				&& !ObjectStatus.INACTIVE.equals(current.getObjectStatus())) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}
	
	@Override
	public List<String> getAllHomeDepartments(ObjectStatus status){
		return dao.getAllHomeDepartments(status);
	}
}