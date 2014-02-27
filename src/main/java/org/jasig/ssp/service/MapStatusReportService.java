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
package org.jasig.ssp.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jasig.ssp.dao.external.ExternalSubstitutableCourseDao;
import org.jasig.ssp.model.MapStatusReport;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalSubstitutableCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.transferobject.reports.MapStatusReportCoachEmailInfo;
import org.jasig.ssp.transferobject.reports.MapStatusReportPerson;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummaryDetail;

public interface MapStatusReportService extends PersonAssocAuditableService<MapStatusReport>  {

	void deleteAllOldReports();

	MapStatusReport evaluatePlan(Set<String> gradesSet, Set<String> criteriaSet,
			Term cutoffTerm, List<Term> allTerms,
			MapStatusReportPerson planIdPersonIdPair, Collection<ExternalSubstitutableCourse> allSubstitutableCourses, boolean termBound, boolean useSubstitutableCourses);

	Set<String> getAdditionalCriteria();

	Set<String> getPassingGrades();

	Term deriveCuttoffTerm();

	Collection<ExternalSubstitutableCourse> getAllSubstitutableCourses();

	List<MapStatusReportSummaryDetail> getSummaryDetails();

	List<MapStatusReportCoachEmailInfo> getCoachesWithOffPlanStudent();
	
	List<MapStatusReportPerson> getOffPlanPlansForOwner(Person coach);

}