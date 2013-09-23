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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.jasig.ssp.model.external.RequisiteCode;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalCourseRequisiteDao extends
		AbstractExternalDataDao<ExternalCourseRequisite> {

	public ExternalCourseRequisiteDao() {
		super(ExternalCourseRequisite.class);
	}

	@SuppressWarnings("unchecked")
	public List<ExternalCourseRequisite> getRequisitesForCourse(
			String requiringCourseCode) {
		if(StringUtils.isBlank(requiringCourseCode))
		{
			return new ArrayList<ExternalCourseRequisite>();
		}
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("requiringCourseCode", requiringCourseCode), Restrictions.and(Restrictions.eq("requiredCourseCode", requiringCourseCode),Restrictions.ne("requisiteCode", RequisiteCode.PRE))));
		return (List<ExternalCourseRequisite>)criteria.list();
	}


	@SuppressWarnings("unchecked")
	public List<ExternalCourseRequisite> getRequisitesForCourses(
			List<String> requiringCourseCode) {
		if(requiringCourseCode == null || requiringCourseCode.isEmpty())
		{
			return new ArrayList<ExternalCourseRequisite>();
		}
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.in("requiringCourseCode", requiringCourseCode));
		return (List<ExternalCourseRequisite>)criteria.list();
	}
}
