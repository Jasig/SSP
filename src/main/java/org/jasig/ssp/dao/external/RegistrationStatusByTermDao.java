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
package org.jasig.ssp.dao.external;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the RegistrationStatusByTerm reference entity.
 */
@Repository
public class RegistrationStatusByTermDao extends
		AbstractExternalDataDao<RegistrationStatusByTerm> {

	public RegistrationStatusByTermDao() {
		super(RegistrationStatusByTerm.class);
	}

	public RegistrationStatusByTerm getForTerm(final Person person,
			final Term term) {
		return getForTerm(person.getSchoolId(), term.getCode());
	}
	
	public RegistrationStatusByTerm getForTerm(final String schoolId,
			final String termCode) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("schoolId", schoolId));
		query.add(Restrictions.eq("termCode", termCode));
		query.add(Restrictions.gt("registeredCourseCount", 0));
		return (RegistrationStatusByTerm) query.uniqueResult();
	}
	
	public PagingWrapper<RegistrationStatusByTerm> getAllForTerm(
			final Term term, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("termCode", term.getCode()));
		query.add(Restrictions.ge("registeredCourseCount", 0));
		return processCriteriaWithSortingAndPaging(query, sAndP, false);
	}

	public PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			final Person person, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("schoolId", person.getSchoolId()));
		query.add(Restrictions.gt("registeredCourseCount", 0));

		return processCriteriaWithSortingAndPaging(query, sAndP, false);
	}
}
