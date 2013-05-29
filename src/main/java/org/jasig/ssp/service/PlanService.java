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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Person service
 */
public interface PlanService extends AbstractPlanService<Plan,PlanTO> {

	Plan copyAndSave(Plan plan) throws CloneNotSupportedException;

	Plan getCurrentForStudent(UUID personId);

	PagingWrapper<Plan> getAllForStudent(
			SortingAndPaging createForSingleSortWithPaging,UUID personId);
	

	List<PlanAdvisorCountTO> getAdvisorsPlanCount(SearchPlanTO form);
	
	List<PlanCourseCountTO> getPlanCourseCount(SearchPlanTO form);
	
	List<PlanStudentStatusTO> getPlanStudentStatusByCourse(SearchPlanTO form);
	
}