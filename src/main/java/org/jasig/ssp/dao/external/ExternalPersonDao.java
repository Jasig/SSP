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

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalPersonDao extends AbstractExternalDataDao<ExternalPerson> {

	public ExternalPersonDao() {
		super(ExternalPerson.class);
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param schoolId
	 *            the schoolId value
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public ExternalPerson getBySchoolId(@NotNull final String schoolId)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(schoolId)) {
			throw new ObjectNotFoundException(schoolId,
					ExternalPerson.class.getName());
		}

		final ExternalPerson obj = (ExternalPerson) createCriteria()
				.add(Restrictions.eq("schoolId", schoolId)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(schoolId,
					ExternalPerson.class.getName());
		}

		return obj;
	}
	
	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param username
	 *            the username value
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public ExternalPerson getByUsername(@NotNull final String username)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new ObjectNotFoundException(username,
					ExternalPerson.class.getName());
		}

		final ExternalPerson obj = (ExternalPerson) createCriteria()
				.add(Restrictions.eq("username", username)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(username,
					ExternalPerson.class.getName());
		}

		return obj;
	}

	public PagingWrapper<ExternalPerson> getBySchoolIds(
			@NotNull final Collection<String> schoolIds,
			final SortingAndPaging sAndP) {

		final Criteria query = createCriteria()
				.add(Restrictions.in("schoolId", schoolIds));

		return processCriteriaWithSortingAndPaging(query, sAndP, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllDepartmentNames(){
		final Criteria query = createCriteria();
		query.setProjection(Projections.projectionList()
						.add(Projections.distinct(Projections.property("departmentName"))));
		
		return (List<String>)query.list();
	}
}
