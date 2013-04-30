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

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalStudentTranscriptTermDao extends
		AbstractExternalDataDao<ExternalStudentTranscriptTerm> {

	protected ExternalStudentTranscriptTermDao() {
		super(ExternalStudentTranscriptTerm.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ExternalStudentTranscriptTerm> getExternalStudentTranscriptTermsBySchoolId(String schoolId){
		Query criteria = createHqlQuery("SELECT transcript.schoolId as transcript_schoolId, " +
				"transcript.creditHoursForGpa as transcript_creditHoursForGpa, " +
				"transcript.creditHoursEarned as transcript_creditHoursEarned, " +
				"transcript.creditHoursAttempted as transcript_creditHoursAttempted, " +
				"transcript.creditHoursNotCompleted as transcript_creditHoursNotCompleted, " +
				"transcript.creditCompletionRate as transcript_creditCompletionRate, " +
				"transcript.totalQualityPoints as transcript_totalQualityPoints, " +
				"transcript.gradePointAverage as transcript_gradePointAverage, " +
				"transcript.termCode as transcript_termCode " +
				"FROM ExternalStudentTranscriptTerm as transcript, Term as transcriptTerm " +
				"WHERE transcript.schoolId = :schoolId AND transcript.termCode = transcriptTerm.code " +
				"ORDER BY transcriptTerm.startDate DESC");
		criteria.setParameter("schoolId", schoolId);
		criteria.setResultTransformer(
				new NamespacedAliasToBeanResultTransformer(
						ExternalStudentTranscriptTerm.class, "transcript_"));
		return (List<ExternalStudentTranscriptTerm>)criteria.list();
	}
	
	public ExternalStudentTranscriptTerm getExternalStudentTranscriptTermBySchoolIdTermCode(String schoolId, String termCode){
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("schoolId", schoolId));
		criteria.add(Restrictions.eq("termCode", termCode));
		return (ExternalStudentTranscriptTerm)criteria.list();
	}
	
}
