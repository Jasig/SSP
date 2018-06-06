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
package org.jasig.ssp.service;

import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanElectiveCourse;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.reference.MessageTemplatePlanPrintParams;
import org.jasig.ssp.transferobject.reports.*;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Person service
 */
public interface PlanService extends AbstractPlanService<Plan,PlanTO,
PlanOutputTO, MessageTemplatePlanPrintParams> {

	Plan copyAndSave(Plan plan) throws CloneNotSupportedException;

	Plan getCurrentForStudent(UUID personId);

	List<Plan> getCurrentPlansForStudents(List<UUID> personIds);

	PagingWrapper<Plan> getAllForStudent(SortingAndPaging createForSingleSortWithPaging,UUID personId);

	List<PlanAdvisorCountTO> getPlanCountByOwner(SearchPlanTO form);
	
	List<PlanCourseCountTO> getPlanCountByCourse(SearchPlanTO form);
	
	List<PlanStudentStatusTO> getPlanStudentStatusByCourse(SearchPlanTO form);

	List<MapStatusReportPerson> getAllActivePlanIds();

	List<MapPlanStatusReportCourse> getAllPlanCoursesForStatusReport(UUID planId);

	PlanElectiveCourse getPlanElectiveCourse(UUID id) throws ObjectNotFoundException;

	List<PersonLiteTO> getAllPlanOwners();

	List<MapTransferGoalReportTO> getTransferGoalReport(List<UUID> transferGoalIds, List<UUID> planOwnerIds, UUID programStatus,
														String planExists, String catalogYear, Date modifiedDateFrom,
														Date modifiedDateTo);
}
