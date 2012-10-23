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

import org.jasig.ssp.dao.PersonEducationGoalDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEducationGoalService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PersonEducationGoal service implementation
 */
@Service
public class PersonEducationGoalServiceImpl implements
		PersonEducationGoalService {

	@Autowired
	private transient PersonEducationGoalDao dao;

	@Override
	public PagingWrapper<PersonEducationGoal> getAll(
			final SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
	}

	@Override
	public PersonEducationGoal get(final UUID id)
			throws ObjectNotFoundException {
		return dao.get(id);
	}

	@Override
	public PersonEducationGoal forPerson(final Person person) {
		return dao.forPerson(person);
	}

	@Override
	public PersonEducationGoal create(final PersonEducationGoal obj) {
		return dao.save(obj);
	}

	@Override
	public PersonEducationGoal save(final PersonEducationGoal obj)
			throws ObjectNotFoundException {
		return dao.save(obj);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonEducationGoal current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.INACTIVE);
			save(current);
		}
	}
}