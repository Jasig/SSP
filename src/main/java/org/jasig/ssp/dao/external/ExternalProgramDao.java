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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.model.external.ExternalProgram;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalProgramDao extends AbstractExternalReferenceDataDao<ExternalProgram> {

	public ExternalProgramDao() {
		super(ExternalProgram.class);
	}

	@SuppressWarnings("unchecked")
	public List<ExternalProgram> facetSearch(String tag, String termCode) {
		Query hqlQuery = createHqlQuery(buildProgramSearchQuery(tag, termCode));
		
		buildProgramSearchParamList(tag, termCode, hqlQuery);
		
		List<ExternalProgram> result = hqlQuery
		.list();
		return result;	
		}

	private void buildProgramSearchParamList(String tag, String termCode,
			Query hqlQuery) {
		if(!StringUtils.isEmpty(termCode))
		{
			hqlQuery.setString("termCode", termCode);
		}
		if(!StringUtils.isEmpty(tag))
		{
			hqlQuery.setString("tag", tag);
		}		
	}

	private String buildProgramSearchQuery(String tag, String termCode) {
		StringBuilder query = new StringBuilder();
		query.append(" select distinct ep from ExternalProgram ep , ExternalCourseProgram ecp , ExternalCourse ec ");
		if(!StringUtils.isEmpty(tag))
		{
			query.append(" ,ExternalCourseTag ectg ");
		}
		if(!StringUtils.isEmpty(termCode))
		{
			query.append(" ,ExternalCourseTerm ectr ");
		}
		query.append(" where ");
		query.append(" ep.code = ecp.programCode ");
		query.append(" and ecp.courseCode = ec.code  ");
		
		if(!StringUtils.isEmpty(tag))
		{
			query.append(" and ec.code = ectg.courseCode ");
			query.append(" and ectg.tag = :tag ");
		}	
		if(!StringUtils.isEmpty(termCode))
		{
			query.append(" and ec.code = ectr.courseCode ");
			query.append(" and ectr.termCode = :termCode  ");
		}
		return query.toString();
	}	

}
