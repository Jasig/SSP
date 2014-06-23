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
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.external.ExternalSubstitutableCourse;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalSubstitutableCourseDao extends AbstractDao<ExternalSubstitutableCourse> {

	public ExternalSubstitutableCourseDao() {
		super(ExternalSubstitutableCourse.class);
	}

	@SuppressWarnings("unchecked")
	public List<ExternalSubstitutableCourse> getAllSubstitutableCourses()
	{
		String getAllSql = " from org.jasig.ssp.model.external.ExternalSubstitutableCourse ";
		return createHqlQuery(getAllSql).list();
		
	}

	@SuppressWarnings("unchecked")
	public Collection<ExternalSubstitutableCourse> getAllPossibleSubstitutableCoursesForStudent(
			Plan plan, List<ExternalStudentTranscriptCourse> transcript) {
		
		//Short Circuit logic, if plan courses or transcript is empty don't try this query
		if(plan.getPlanCourses().isEmpty() || transcript.isEmpty())
			return new ArrayList<ExternalSubstitutableCourse>();
		List<String> sourceCourseCodes = new ArrayList<String>();
		List<String> targetCourseCodes = new ArrayList<String>();

		List<PlanCourse> planCourses = plan.getPlanCourses();
		for (PlanCourse planCourse : planCourses) {
			sourceCourseCodes.add(planCourse.getFormattedCourse());
		}
		for (ExternalStudentTranscriptCourse transcriptCourse : transcript) {
			targetCourseCodes.add(transcriptCourse.getFormattedCourse());
		}
		Criteria criteriaQuery = createCriteria().add(Restrictions.or(Restrictions.in("sourceFormattedCourse", sourceCourseCodes), Restrictions.in("targetFormattedCourse", targetCourseCodes)));
		return criteriaQuery.list();
	}
}