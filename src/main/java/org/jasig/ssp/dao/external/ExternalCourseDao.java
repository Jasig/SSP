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

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalCourseDao extends AbstractExternalDataDao<ExternalCourse> {

	public ExternalCourseDao() {
		super(ExternalCourse.class);
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param courseCode
	 *            the courseCode value
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	public ExternalCourse getByCourseCode(@NotNull final String courseCode)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(courseCode)) {
			throw new ObjectNotFoundException(courseCode,
					ExternalCourse.class.getName());
		}

		final ExternalCourse obj = (ExternalCourse) createCriteria()
				.add(Restrictions.eq("code", courseCode)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(courseCode,
					ExternalCourse.class.getName());
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
	@SuppressWarnings("unchecked")
	public List<ExternalCourse> getByKeyword(@NotNull final String keyword)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(keyword)) {
			throw new ObjectNotFoundException(keyword,
					ExternalCourse.class.getName());
		}

		List<ExternalCourse> result = createHqlQuery( "select from org.jasig.ssp.model.external.ExternalCourse ec where upper(:keyword%) " +
				" like upper(ec.title) or upper(:keyword%) like upper(ec.formattedCourse) order by ec.formattedCourse asc" )
		.setEntity( "keyword", keyword )
		.list();

		return result;
	}

	@Override
	@Deprecated
	public PagingWrapper<ExternalCourse> getAll(ObjectStatus status) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ExternalCourse> getAll() {
		List<ExternalCourse> result = createCriteria().list();
		return result;
	}

	@Override
	@Deprecated
	public PagingWrapper<ExternalCourse> getAll(SortingAndPaging sAndP) {
		return null;
	}
}
