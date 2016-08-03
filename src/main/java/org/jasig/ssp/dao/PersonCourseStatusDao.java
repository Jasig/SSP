/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonCourseStatus;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class PersonCourseStatusDao extends AbstractAuditableCrudDao<PersonCourseStatus>
		implements AuditableCrudDao<PersonCourseStatus> {

	public PersonCourseStatusDao() {
		super(PersonCourseStatus.class);
	}

	protected PersonCourseStatusDao(Class<PersonCourseStatus> persistentClass) {
		super(persistentClass);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PersonCourseStatus> getAllForPerson(Person person) {
		Criteria criteria = createCriteria().add(Restrictions.eq("person", person))
						.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		return criteria.list();
	}
}
