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
package org.jasig.ssp.dao.external;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.transferobject.reports.SpecialServiceStudentCoursesTO;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ExternalStudentTranscriptCourseDao extends AbstractExternalDataDao<ExternalStudentTranscriptCourse> {

    @Autowired
    private transient ScheduledApplicationTaskStatusService scheduledApplicationTaskService;


	public ExternalStudentTranscriptCourseDao() {
		super(ExternalStudentTranscriptCourse.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolId(String schoolId){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		return (List<ExternalStudentTranscriptCourse>)criteria.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolIdAndTermCode(String schoolId, String termCode){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.add(Restrictions.eq("termCode", termCode));
		return (List<ExternalStudentTranscriptCourse>)criteria.list();
	}

    /**
     * Returns SSG Course Report TOs by specified criteria.
     *  SchoolIds *must* be populated! The rest can be empty/null.
     *   This is batched.
     * @param schoolIds the list of school ids
     * @param termCodes the list of term codes
     * @param grades the list of grades
     * @param statuses the list of statuses
     * @return the list of special service student course transfer objects
     */
    public List<SpecialServiceStudentCoursesTO> getTranscriptCoursesBySchoolIds(
            final List<String> schoolIds, final List<String> termCodes, final List<String> grades,
                                                                        final List<String> statuses) {
        if (CollectionUtils.isEmpty(schoolIds)) {
            return Lists.newArrayList();
        }

        final String fromClause = "from ExternalStudentTranscriptCourse as estc ";
        final StringBuilder whereClause = new StringBuilder("where estc.schoolId in (:schoolIds) ");

        if (CollectionUtils.isNotEmpty(termCodes)) {
            whereClause.append(" and estc.termCode in (:termCodes) ");
        }

        if (CollectionUtils.isNotEmpty(grades)) {
            whereClause.append(" and estc.grade in (:grades) ");
        }

        if (CollectionUtils.isNotEmpty(statuses)) {
            whereClause.append(" and estc.statusCode in (:statuses) ");
        }

        final String specialServiceCourseReportHQLQuery = "select "
            +   "estc.schoolId as ssgCourseReport_schoolId, "
            +   "estc.firstName as ssgCourseReport_firstName, "
            +   "estc.lastName as ssgCourseReport_lastName, "
            +   "estc.title as ssgCourseReport_courseTitle, "
            +   "estc.formattedCourse as ssgCourseReport_formattedCourse, "
            +   "estc.termCode as ssgCourseReport_termCode, "
            +   "estc.statusCode as ssgCourseReport_statusCode, "
            +   "estc.grade as ssgCourseReport_grade, "
            +   "estc.facultySchoolId as ssgCourseReport_facultySchoolId "
            + fromClause + whereClause
            +" group by estc.schoolId, estc.firstName, estc.lastName, estc.title, estc.formattedCourse, estc.termCode, "
            +    "estc.statusCode, estc.grade, estc.facultySchoolId "
            + "order by estc.schoolId, estc.termCode asc";

        final BatchProcessor<String, SpecialServiceStudentCoursesTO> processor = new BatchProcessor<>(schoolIds);
        do {
            final Query query = createHqlQuery(specialServiceCourseReportHQLQuery).setResultTransformer(
                    new NamespacedAliasToBeanResultTransformer(SpecialServiceStudentCoursesTO.class, "ssgCourseReport_"));

            if (CollectionUtils.isNotEmpty(termCodes)) {
                query.setParameterList("termCodes", termCodes);
            }

            if (CollectionUtils.isNotEmpty(grades)) {
                query.setParameterList("grades", grades);
            }

            if (CollectionUtils.isNotEmpty(statuses)) {
                query.setParameterList("statuses", statuses);
            }

            processor.process(query, "schoolIds");
        } while (processor.moreToProcess());

        return processor.getSortedAndPagedResultsAsList();
    }
}