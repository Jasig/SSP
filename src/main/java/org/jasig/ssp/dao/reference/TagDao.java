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
package org.jasig.ssp.dao.reference;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.external.ExternalProgram;
import org.jasig.ssp.model.reference.Tag;

/**
 * Data access class for the Tag reference entity.
 */
@Repository
public class TagDao extends AbstractReferenceAuditableCrudDao<Tag>
		implements AuditableCrudDao<Tag> {

	public TagDao() {
		super(Tag.class);
	}

	@SuppressWarnings("unchecked")
	public List<Tag> facetSearch(String programCode, String termCode) {
		String query = buildTagSearchQuery(programCode, termCode);		
		Query hqlQuery = createHqlQuery(query.toString());
		buildTagSearchParamList(programCode, termCode, hqlQuery);		
		List<Tag> result = hqlQuery
		.list();
		return result;	
		}

	private void buildTagSearchParamList(String programCode, String termCode,
			Query hqlQuery) {
		if(!StringUtils.isEmpty(programCode))
		{
			hqlQuery.setString("programCode", programCode);
		}
		if(!StringUtils.isEmpty(termCode))
		{
			hqlQuery.setString("termCode", termCode);
		}		
	}

	private String buildTagSearchQuery(String programCode, String termCode) {
		StringBuilder query = new StringBuilder();
		query.append(" select distinct t from Tag t , ExternalCourseTag ect , ExternalCourse ec ");
		if(!StringUtils.isEmpty(programCode))
		{
			query.append(" ,ExternalCourseProgram ecp ");
		}
		if(!StringUtils.isEmpty(termCode))
		{
			query.append(" ,ExternalCourseTerm ectr ");
		}
		query.append(" where ");
		query.append(" t.code = ect.tag ");
		query.append(" and ect.courseCode = ec.code  ");
		
		if(!StringUtils.isEmpty(programCode))
		{
			query.append(" and ec.code = ecp.courseCode ");
			query.append(" and ecp.programCode = :programCode ");
		}	
		if(!StringUtils.isEmpty(termCode))
		{
			query.append(" and ec.code = ectr.courseCode ");
			query.append(" and ectr.termCode = :termCode  ");
		}
		return query.toString();		
	}
}
