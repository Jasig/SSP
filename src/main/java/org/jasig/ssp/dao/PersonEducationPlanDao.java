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
package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonEducationPlan model.
 */
@Repository
public class PersonEducationPlanDao extends
		AbstractAuditableCrudDao<PersonEducationPlan> implements
		AuditableCrudDao<PersonEducationPlan> {

	/**
	 * Constructor
	 */
	public PersonEducationPlanDao() {
		super(PersonEducationPlan.class);
	}

	/**
	 * Return the education plan for the specified Person.
	 * 
	 * @param person
	 *            Lookup the education plan for this Person.
	 * 
	 * @return The education plan for the specified Person.
	 */
	public PersonEducationPlan forPerson(final Person person) {
		final Criteria query = sessionFactory.getCurrentSession()
				.createCriteria(PersonEducationPlan.class)
				.add(Restrictions.eq("person", person));
		return (PersonEducationPlan) query.uniqueResult();
	}
}