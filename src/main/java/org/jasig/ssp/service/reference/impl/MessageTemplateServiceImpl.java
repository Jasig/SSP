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
package org.jasig.ssp.service.reference.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.StrengthTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.reference.AbstractMessageTemplateMapPrintParamsTO;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MessageTemplate service
 */
@Service
@Transactional
public class MessageTemplateServiceImpl extends
		AbstractReferenceService<MessageTemplate> implements
		MessageTemplateService {

	@Autowired
	transient private MessageTemplateDao dao;

	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	/**
	 * Sets the DAO for use by the super class methods
	 * 
	 * @param dao
	 *            Data access object
	 */
	protected void setDao(final MessageTemplateDao dao) {
		this.dao = dao;
	}

	@Override
	protected MessageTemplateDao getDao() {
		return dao;
	}

	@Autowired
	private transient ConfigService configService;

	private static final String STUDENTUIPATH = "MyGPS";

	private String getServerExternalPath() {
		return configService.getByNameNull("serverExternalPath");
	}

	private String getInstitutionName() {
		return configService.getByNameNull("inst_name");
	}
	
	private String getInstitutionHomeUrl() {
		return configService.getByNameNull("inst_home_url");
	}
	
	private String getAppTitle() {
		return configService.getByNameNull("app_title");
	}
	
	private void setInstitutionValues(Map<String, Object> messageParams){
		messageParams.put("institutionName", getInstitutionName());
		messageParams.put("institutionHomeUrl", getInstitutionHomeUrl());
		messageParams.put("applicationTitle", getAppTitle());
		messageParams.put("linkToSSP", getServerExternalPath());
	}
	
	private SubjectAndBody populateFromTemplate(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters) {
		try {
			setInstitutionValues(templateParameters);
			final MessageTemplate messageTemplate = dao.get(messageTemplateId);

			final String subject = velocityTemplateService
					.generateContentFromTemplate(messageTemplate.getSubject(),
							messageTemplate.subjectTemplateId(),
							templateParameters);

			final String body = velocityTemplateService
					.generateContentFromTemplate(messageTemplate.getBody(),
							messageTemplate.bodyTemplateId(),
							templateParameters);

			return new SubjectAndBody(subject, body);
		} catch (final ObjectNotFoundException e) {
			throw new ConfigException(messageTemplateId,
					ConfigException.TEMPLATE_TYPE, e);
		}
	}

	private String formatDate(final Date date) {
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}

	@Override
	public SubjectAndBody createContactCoachMessage(final String body,
			final String subject, final Person student) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("subject", subject);
		messageParams.put("message", body);
		messageParams.put("student", student);
		messageParams.put("fullName", student.getFullName());
		return populateFromTemplate(MessageTemplate.CONTACT_COACH_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createActionPlanStepMessage(final Task task) {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("task", task);
		messageParams.put("dueDateFormatted", formatDate(task.getDueDate()));
		return populateFromTemplate(MessageTemplate.ACTION_PLAN_STEP_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createCustomActionPlanTaskMessage(final Task task) {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("task", task);
		messageParams.put("dueDateFormatted", formatDate(task.getDueDate()));
		return populateFromTemplate(MessageTemplate.CUSTOM_ACTION_PLAN_TASK_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createActionPlanMessage(final Person student,
			final List<TaskTO> taskTOs, final List<GoalTO> goalTOs,  List<StrengthTO> strengthTOs) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskTOs", taskTOs);
		messageParams.put("goalTOs", goalTOs);
		messageParams.put("strengthTOs", strengthTOs);
		messageParams.put("student", student);
		messageParams.put("fullName", student.getFullName());
		return populateFromTemplate(MessageTemplate.ACTION_PLAN_EMAIL_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createStudentIntakeTaskMessage(final Task task) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskName", task.getName());
		messageParams.put("student", task.getPerson());
		messageParams.put("fullName", task.getPerson().getFullName());
		// fix links in description
		final String linkedDescription = task.getDescription()
				.replaceAll(
						"href=\"/" + STUDENTUIPATH + "/",
						"href=\"" + getServerExternalPath() + "/"
								+ STUDENTUIPATH
								+ "/");
		messageParams.put("description", linkedDescription);

		messageParams.put("dueDateFormatted",task.getDueDate() == null ? "" : formatDate(task.getDueDate()));

		return populateFromTemplate(
				MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createJournalNoteForEarlyAlertResponseMessage(
			final String termToRepresentEarlyAlert, final EarlyAlert earlyAlert) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("termToRepresentEarlyAlert",
				termToRepresentEarlyAlert);
		messageParams.put("earlyAlert", earlyAlert);
		return populateFromTemplate(
				MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createAdvisorConfirmationForEarlyAlertMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertToStudentMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_MESSAGETOSTUDENT_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertFacultyConfirmationMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_CONFIRMATIONTOFACULTY_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertAdvisorConfirmationMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertResponseToFacultyMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_RESPONSETOFACULTYFROMCOACH_ID,
				messageParams);
	}
	
	@Override
	public SubjectAndBody createEarlyAlertResponseRequiredToCoachMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EARLYALERT_RESPONSE_REQUIRED_ID,
				messageParams);
	}
	
	@Override
	public <TOO extends AbstractPlanOutputTO<T, TO>, T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanMatrixOutput(
			AbstractMessageTemplateMapPrintParamsTO<TOO, T, TO> params) {
		
		final Map<String, Object> messageParams =  addParamsToMapPlan(
				params.getStudent(), 
				params.getOwner(), 
				params.getOutputPlan().getNonOutputTO(),
				params.getTotalPlanCreditHours(),
				params.getTermCourses(),
				params.getInstitutionName());
		messageParams.put("printParams", params);
		return populateFromTemplate(params.getMessageTemplateId(), messageParams);
	}
	
	@Override
	public <T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanFullOutput(final Person student,final Person owner, 
			final AbstractPlanOutputTO<T, TO> planOutput, 
			final Float totalPlanCreditHours,
			final Float totalPlanDevHours,
			final List<TermCourses<T, TO>> termCourses,
			final String institutionName) {
 
		final Map<String, Object> messageParams = addParamsToMapPlan(
				student,
				owner, 
				planOutput.getNonOutputTO(), 
				totalPlanCreditHours,
				termCourses,
				institutionName);
 
		messageParams.put("isPrivate", planOutput.getIsPrivate());
		messageParams.put("includeCourseDescription", planOutput.getIncludeCourseDescription());
		messageParams.put("includeFinancialAidInformation", planOutput.getIncludeFinancialAidInformation());
		messageParams.put("includeHeaderFooter", planOutput.getIncludeHeaderFooter());
		messageParams.put("includeTotalTimeExpected", planOutput.getIncludeTotalTimeExpected());
		messageParams.put("totalPlanDevHours", totalPlanDevHours);
		messageParams.put("planContactNotes", planOutput.getNonOutputTO().getContactNotes());
		messageParams.put("planStudentNotes", planOutput.getNonOutputTO().getStudentNotes());
		
		messageParams.put("contactEmail", planOutput.getNonOutputTO().getContactEmail());
		messageParams.put("contactPhone", planOutput.getNonOutputTO().getContactPhone());
		messageParams.put("contactTitle", planOutput.getNonOutputTO().getContactTitle());
		messageParams.put("contactName", planOutput.getNonOutputTO().getContactName());
		if(planOutput.getFinancialAid() != null){
			messageParams.put("neededFor67PercentCompletion", planOutput.getFinancialAid().getNeededFor67PtcCompletion());
			messageParams.put("financialAidGpa", planOutput.getFinancialAid().getFinancialAidGpa());
			messageParams.put("hoursNeededB", planOutput.getFinancialAid().getGpa20BHrsNeeded());
		}
		if(planOutput.getGpa() != null){
			messageParams.put("attemptedHours", planOutput.getGpa().getCreditHoursAttempted());
			messageParams.put("completedHours", planOutput.getGpa().getCreditHoursEarned());
			messageParams.put("completionRage", planOutput.getGpa().getCreditCompletionRate());
		}
		
		return populateFromTemplate(MessageTemplate.OUTPUT_MAP_PLAN_FULL_ID,
				messageParams);
	}
	
	private <T extends AbstractPlan,TO extends AbstractPlanTO<T>> Map<String, Object> addParamsToMapPlan(
			final Person student,
			final Person owner, 
			final TO plan, 
			final Float totalPlanCreditHours,
			final List<TermCourses<T,TO>> termCourses,
			final String institutionName){
		
		final Map<String, Object> messageParams = new HashMap<String, Object>(); 
		
		messageParams.put("title", plan.getName());
		messageParams.put("plan", plan);
		SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
		messageParams.put("lastModified", plan.getModifiedDate() == null ? null : formatter.format(plan.getModifiedDate()));
		messageParams.put("planContactNotes", plan.getContactNotes());
		messageParams.put("planStudentNotes", plan.getStudentNotes());
		messageParams.put("termCourses", termCourses);
		if(student != null)
			addStudent(messageParams, student);
		
		messageParams.put("contactPhone", plan.getContactPhone());
		messageParams.put("contactName", plan.getContactName());
		messageParams.put("contactEmail", plan.getContactEmail());
		messageParams.put("contactTitle", plan.getContactTitle());
		

		
		messageParams.put("ownerPhone", owner.getWorkPhone());
		messageParams.put("ownerFullName", owner.getFullName());
		messageParams.put("ownerEmail", owner.getPrimaryEmailAddress());
		
		messageParams.put("totalPlanHours", totalPlanCreditHours);
		messageParams.put("institution", institutionName);
		messageParams.put("createdDateFormatted", formatDate(new Date()));
		
		return messageParams;
		
	}
	
	private void addStudent(Map<String,Object> messageParams, Person student){
			messageParams.put("student", student);
			messageParams.put("studentFullName", student.getFullName());
			messageParams.put("studentEmail", student.getPrimaryEmailAddress());
			messageParams.put("studentSchoolId", student.getSchoolId());
	}

	@Override
	public SubjectAndBody createMapStatusReportEmail(
			MapStatusReportSummary summary) {
		final Map<String, Object> messageParams = new HashMap<String, Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy HH:mm:ss z");
		messageParams.put("startTime", sdf.format(summary.getStartTime().getTime()));
		messageParams.put("endTime", sdf.format(summary.getEndTime().getTime()));
		messageParams.put("totalPlans", summary.getStudentsInScope());
		messageParams.put("title", "Map Status Report");
		messageParams.put("reportSummaryDetails", summary.getSummaryDetails());
		messageParams.put("termConfigReminder", false);
		messageParams.put("error", false);
		
		return populateFromTemplate(MessageTemplate.MAP_STATUS_REPORT_ID,
				messageParams);
	}

	//TODO GENERATE EARLYALERT_RESPONSE_TO_REFERRAL
	@Override
	public SubjectAndBody createEarlyAlertResponseToReferralSourceMessage(
			Map<String, Object> messageParams) {
			return populateFromTemplate(
					MessageTemplate.EARLYALERT_RESPONSE_TO_REFERRAL_SOURCE_ID,
					messageParams);
	}
}