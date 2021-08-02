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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ExternalStudentTranscriptNonCourseDao extends AbstractExternalDataDao<ExternalStudentTranscriptNonCourseEntity> {

	public ExternalStudentTranscriptNonCourseDao () {
		super(ExternalStudentTranscriptNonCourseEntity.class);
	}


    /**
     * No sorting and paging
     * @return the list of all external student transcript non course entity objects
     */
    @SuppressWarnings("unchecked")
    public List<ExternalStudentTranscriptNonCourseEntity> getAllNonCourseTranscripts() {
        String getAllSql = " from org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity ";
        return createHqlQuery(getAllSql).list();
    }

	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptNonCourseEntity> getNonCourseTranscriptsBySchoolId(String schoolId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		return (List<ExternalStudentTranscriptNonCourseEntity>)criteria.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptNonCourseEntity> getNonCourseTranscriptsBySchoolIdAndTermCode(String schoolId, String termCode) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.add(Restrictions.eq("termCode", termCode));
		return (List<ExternalStudentTranscriptNonCourseEntity>)criteria.list();
	}
}
