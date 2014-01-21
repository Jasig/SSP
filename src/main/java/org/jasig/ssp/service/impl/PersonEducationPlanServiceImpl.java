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

import java.util.UUID;

import org.jasig.ssp.dao.PersonEducationPlanDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEducationPlanService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonEducationPlan service implementation
 */
@Service
public class PersonEducationPlanServiceImpl implements
		PersonEducationPlanService {

	@Autowired
	private transient PersonEducationPlanDao dao;

	@Autowired
	private transient StudentStatusService studentStatusService;

	@Override
	public PagingWrapper<PersonEducationPlan> getAll(
			final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonEducationPlan get(final UUID id)
			throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonEducationPlan forPerson(final Person person) {
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationPlan create(final PersonEducationPlan obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationPlan save(final PersonEducationPlan obj)
			throws ObjectNotFoundException {
		final PersonEducationPlan current = get(obj.getId());

		current.setObjectStatus(obj.getObjectStatus());
		if (obj.getStudentStatus() != null) {
			current.setStudentStatus(studentStatusService.get(obj
					.getStudentStatus().getId()));
		}
		current.setCollegeDegreeForParents(obj.isCollegeDegreeForParents());
		current.setSpecialNeeds(obj.isSpecialNeeds());
		current.setGradeTypicallyEarned(obj.getGradeTypicallyEarned());

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonEducationPlan current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}
}