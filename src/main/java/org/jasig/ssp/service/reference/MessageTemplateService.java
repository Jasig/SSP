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
package org.jasig.ssp.service.reference;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.TaskTO;

/**
 * MessageTemplate service
 */
public interface MessageTemplateService extends
		ReferenceService<MessageTemplate> {

	SubjectAndBody createContactCoachMessage(String body, String subject,
			Person student);

	SubjectAndBody createActionPlanStepMessage(final Task task);

	SubjectAndBody createCustomActionPlanTaskMessage(final Task task);

	SubjectAndBody createActionPlanMessage(Person student,
			List<TaskTO> taskTOs, List<GoalTO> goalTOs);

	SubjectAndBody createJournalNoteForEarlyAlertResponseMessage(
			String termToRepresentEarlyAlert, EarlyAlert earlyAlert);

	SubjectAndBody createStudentIntakeTaskMessage(Task task);

	SubjectAndBody createAdvisorConfirmationForEarlyAlertMessage(
			Map<String, Object> messageParams);

	SubjectAndBody createEarlyAlertToStudentMessage(
			Map<String, Object> messageParams);

	SubjectAndBody createEarlyAlertFacultyConfirmationMessage(
			Map<String, Object> messageParams);

	SubjectAndBody createEarlyAlertAdvisorConfirmationMessage(
			Map<String, Object> messageParams);

	SubjectAndBody createEarlyAlertResponseToFacultyMessage(
			Map<String, Object> messageParams);
	
	public SubjectAndBody createMapPlanMatrixOutput(final Person student, final Person owner, final PlanTO plan, final Float totalPlanCreditHours,
			final List<TermCourses> termCourses,
			String institutionName);
	
	public SubjectAndBody createMapPlanFullOutput(final Person student, final Person owner, final PlanOutputTO plan, final Float totalPlanCreditHours,
			final List<TermCourses> termCourses,
			String institutionName);
}