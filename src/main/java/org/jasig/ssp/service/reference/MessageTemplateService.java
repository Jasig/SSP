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

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.StrengthTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.messagetemplate.TaskMessageTemplateTO;
import org.jasig.ssp.transferobject.reference.AbstractMessageTemplateMapPrintParamsTO;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummary;

/**
 * MessageTemplate service
 */
public interface MessageTemplateService extends
		ReferenceService<MessageTemplate> {

	SubjectAndBody createContactCoachMessage(String body, String subject,
			Person student);

	SubjectAndBody createActionPlanStepMessage(final TaskMessageTemplateTO task);

	SubjectAndBody createCustomActionPlanTaskMessage(final TaskMessageTemplateTO task);

	SubjectAndBody createActionPlanMessage(Person student,
			List<TaskTO> taskTOs, List<GoalTO> goalTOs,  List<StrengthTO> strengthTOs);

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
	
	 SubjectAndBody createEarlyAlertResponseRequiredToCoachMessage(
				final Map<String, Object> messageParams);
	
	SubjectAndBody createEarlyAlertResponseToReferralSourceMessage(
			Map<String, Object> messageParams);

    SubjectAndBody createMyGPSWelcomeMessage();

	
	public <TOO extends AbstractPlanOutputTO<T, TO>, T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanMatrixOutput(
			AbstractMessageTemplateMapPrintParamsTO<TOO, T, TO> params, Map<String,Object> additionalParams);
	
	public  <T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanFullOutput(final Person student, final Person owner, final AbstractPlanOutputTO<T,TO> plan, 
			final BigDecimal totalPlanCreditHours,
			final BigDecimal totalPlanDevHours,
			final List<TermCourses<T, TO>> termCourses,
			final String institutionName);

	SubjectAndBody createMapStatusReportEmail(MapStatusReportSummary summary);
}