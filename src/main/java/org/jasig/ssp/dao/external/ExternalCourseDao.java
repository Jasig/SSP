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

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalCourseDao extends AbstractExternalReferenceDataDao<ExternalCourse> {

	public ExternalCourseDao() {
		super(ExternalCourse.class);
	}


	@SuppressWarnings("unchecked")
	public List<ExternalCourse> getAll() {
		Criteria criteria = createCriteria();
		criteria.addOrder(Order.asc("formattedCourse"));
		List<ExternalCourse> result = criteria.list();
		return result;
	}

	@Override
	@Deprecated
	public PagingWrapper<ExternalCourse> getAll(SortingAndPaging sAndP) {
		throw new NotImplementedException("Use getAll()");
	}

	@SuppressWarnings("unchecked")
	public List<Tag> getTagsForCourse(String code) {
		String getTagsForCourseBaseQuery = "select distinct tag from Tag tag, ExternalCourseTag ect where tag.code = ect.tag and ect.courseCode = :courseCode";
		return createHqlQuery(getTagsForCourseBaseQuery).setString("courseCode", code).list();
	}
	public Boolean validateCourseForTerm(String code, String termCode) {
		String baseValidateCourseHqlQuery = "from ExternalCourseTerm ect where ect.courseCode = :courseCode and ect.termCode = :termCode";
		return createHqlQuery(baseValidateCourseHqlQuery)
		.setString("courseCode", code)
		.setString("termCode", termCode)
		.list().size() > 0 ;
	}


	@SuppressWarnings("unchecked")
	public List<ExternalCourse> search(String programCode, String tag,
			String termCode) {
		Query hqlQuery = createHqlQuery(buildCourseSearchQuery(programCode, tag, termCode));
		
		buildCourseSearchParamList(programCode, tag, termCode, hqlQuery);	
		
		List<ExternalCourse> result = hqlQuery
		.list();

		return result;
	}


	private void buildCourseSearchParamList(String programCode, String tag,
			String termCode, Query hqlQuery) {
		if(!StringUtils.isEmpty(programCode))
		{
			hqlQuery.setString("programCode", programCode);
		}
		if(!StringUtils.isEmpty(termCode))
		{
			hqlQuery.setString("termCode", termCode);
		}
		if(!StringUtils.isEmpty(tag))
		{
			hqlQuery.setString("tag", tag);
		}
	}


	private String buildCourseSearchQuery(String programCode, String tag,
			String termCode) {
		boolean firstWhereCondition = true;
		StringBuilder query = new StringBuilder();
		query.append(" select distinct ec from ExternalCourse ec ");
		if(!StringUtils.isEmpty(programCode))
		{
			query.append(" ,ExternalCourseProgram ecp ");
		}
		if(!StringUtils.isEmpty(tag))
		{
			query.append(" ,ExternalCourseTag ectg ");
		}
		if(!StringUtils.isEmpty(termCode))
		{
			query.append(" ,ExternalCourseTerm ectr ");
		}
		if(!StringUtils.isEmpty(programCode))
		{
			query.append(" where ");
			query.append(" ec.code = ecp.courseCode ");
			query.append(" and ecp.programCode = :programCode  ");
			firstWhereCondition = false;
		}	
		if(!StringUtils.isEmpty(tag))
		{
			if(!firstWhereCondition)
			{
				query.append(" and ");
			}
			else
			{
				query.append(" where ");
			}
			query.append(" ec.code = ectg.courseCode ");
			query.append(" and ectg.tag = :tag ");
			firstWhereCondition = false;
		}	
		if(!StringUtils.isEmpty(termCode))
		{
			if(!firstWhereCondition)
			{
				query.append(" and ");
			}
			else
			{
				query.append(" where ");
			}			
			query.append(" ec.code = ectr.courseCode ");
			query.append(" and ectr.termCode = :termCode  ");
		}
		return query.toString();
	}
}
