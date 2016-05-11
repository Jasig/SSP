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
package org.jasig.ssp.service.reference.impl;

import org.apache.commons.lang.StringUtils;
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
import org.jasig.ssp.transferobject.messagetemplate.CoachPersonLiteMessageTemplateTO;
import org.jasig.ssp.transferobject.messagetemplate.TaskMessageTemplateTO;
import org.jasig.ssp.transferobject.reference.AbstractMessageTemplateMapPrintParamsTO;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummary;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.MessageTemplatePreviewTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

			MessageTemplateTO messageTemplateTO = populateFromTemplateAsMessageTempateTO(messageTemplateId, templateParameters);
			return new SubjectAndBody(messageTemplateTO.getSubject(), messageTemplateTO.getBody());
	}
	private MessageTemplateTO populateFromTemplateAsMessageTempateTO(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)  {
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

			MessageTemplateTO messageTemplateTO = new MessageTemplateTO(messageTemplate);
			messageTemplateTO.setSubject(subject);
			messageTemplateTO.setBody(body);
			return messageTemplateTO;
		} catch (final ObjectNotFoundException e) {
			throw new ConfigException(messageTemplateId,
					ConfigException.TEMPLATE_TYPE, e);
		}
	}

	private String formatDate(final Date date) {
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}

	@Override
	public SubjectAndBody createBulkAddCaseloadReassignmentMessage(int successCount, List<String> errors) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("successCount", successCount);
		messageParams.put("errors", errors);

		return populateFromTemplate(MessageTemplate.BULK_ADD_CASELOAD_REASSIGNMENT_ID,
				messageParams);
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
	public SubjectAndBody createActionPlanStepMessage(final TaskMessageTemplateTO task) {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("task", task);
		messageParams.put("dueDateFormatted", formatDate(task.getDueDate()));
		return populateFromTemplate(MessageTemplate.ACTION_PLAN_STEP_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createCustomActionPlanTaskMessage(final TaskMessageTemplateTO task) {
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
	public SubjectAndBody createCoachingAssignmentChangeMessage(
			final Map<String, Object> messageParams) {
		return populateFromTemplate(
				MessageTemplate.EMAIL_COACHING_ASSIGNMENT_CHANGE_ID,
				messageParams);
	}

	@Override
	public <TOO extends AbstractPlanOutputTO<T, TO>, T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanMatrixOutput(
			AbstractMessageTemplateMapPrintParamsTO<TOO, T, TO> params, Map<String,Object> additionalParams) {
		
		final Map<String, Object> messageParams =  addParamsToMapPlan(
				params.getStudent(), 
				params.getOwner(),
				params.getLastModifiedBy(),
				params.getOutputPlan().getNonOutputTO(),
				params.getTotalPlanCreditHours(),
				params.getTermCourses(),
				params.getInstitutionName(),
				params.getOutputPlan().getNotes());
		if(additionalParams != null)
			messageParams.put("printParams", additionalParams);
		return populateFromTemplate(params.getMessageTemplateId(), messageParams);
	}
	
	@Override
	public <T extends AbstractPlan,TO extends AbstractPlanTO<T>> SubjectAndBody createMapPlanFullOutput(final Person student,final Person owner, final Person modifiedBy,
			final AbstractPlanOutputTO<T, TO> planOutput, 
			final BigDecimal totalPlanCreditHours,
			final BigDecimal totalPlanDevHours,
			final List<TermCourses<T, TO>> termCourses,
			final String institutionName,
			final Map<String,Object> additionalParams) {
 
		final Map<String, Object> messageParams = addParamsToMapPlan(
				student,
				owner,
				modifiedBy,
				planOutput.getNonOutputTO(), 
				totalPlanCreditHours,
				termCourses,
				institutionName,
				planOutput.getNotes());

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
		if(additionalParams != null)
			messageParams.put("printParams", additionalParams);

		return populateFromTemplate(MessageTemplate.OUTPUT_MAP_PLAN_FULL_ID,
				messageParams);
	}
	
	private <T extends AbstractPlan,TO extends AbstractPlanTO<T>> Map<String, Object> addParamsToMapPlan(
			final Person student,
			final Person owner,
			final Person modifiedBy,
			final TO plan, 
			final BigDecimal totalPlanCreditHours,
			final List<TermCourses<T,TO>> termCourses,
			final String institutionName,
			final String emailNotes){
		
		final Map<String, Object> messageParams = new HashMap<String, Object>(); 
		
		messageParams.put("title", plan.getName());
		messageParams.put("plan", plan);
		SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
		messageParams.put("lastModified", plan.getModifiedDate() == null ? null : formatter.format(plan.getModifiedDate()));
		messageParams.put("planContactNotes", plan.getContactNotes());
		messageParams.put("planStudentNotes", plan.getStudentNotes());
		messageParams.put("termCourses", termCourses);

		if(student != null) {
			addStudent(messageParams, student);
			Person coach = student.getCoach();
			if (coach != null) {
				messageParams.put("coach", new CoachPersonLiteMessageTemplateTO(coach));
			}
		}

		messageParams.put("contactPhone", plan.getContactPhone());
		messageParams.put("contactName", plan.getContactName());
		messageParams.put("contactEmail", plan.getContactEmail());
		messageParams.put("contactTitle", plan.getContactTitle());



		messageParams.put("ownerPhone", owner.getWorkPhone());
		messageParams.put("ownerFullName", owner.getFullName());
		messageParams.put("ownerEmail", owner.getPrimaryEmailAddress());
		if(StringUtils.isNotBlank(emailNotes))
			messageParams.put("emailNotes", emailNotes.trim());
		else
			messageParams.put("emailNotes", "");
		
		messageParams.put("totalPlanHours", totalPlanCreditHours);
		messageParams.put("institution", institutionName);
		messageParams.put("createdDateFormatted", formatDate(new Date()));

		if (modifiedBy!=null) {
			messageParams.put("lastModifiedBy", new CoachPersonLiteMessageTemplateTO(modifiedBy));
		}
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

    @Override
    public SubjectAndBody createMyGPSWelcomeMessage () {
        final Map<String, Object> messageParams = new HashMap<String, Object>();
        setInstitutionValues(messageParams);
        return populateFromTemplate(MessageTemplate.MYGPS_WELCOME_MESSAGE_ID, messageParams);
    }

	public MessageTemplateTO createMessageTemplatePreview (UUID id) {
		final Map<String, Object> messageParams;

		if ( id.equals(MessageTemplate.ACTION_PLAN_EMAIL_ID) ) {
			messageParams = createPreviewActionPlanEmailMessageParams();
		} else if (id.equals(MessageTemplate.ACTION_PLAN_STEP_ID)) {
			messageParams = createPreviewActionPlanStepEmailMessageParams();
		} else if (id.equals(MessageTemplate.EMAIL_COACHING_ASSIGNMENT_CHANGE_ID)) {
			messageParams = createPreviewCoachingAssignmentChangeMessageMessageParams();
		} else if (id.equals(MessageTemplate.CONTACT_COACH_ID)) {
			messageParams = createContactCoachEmailMessageParams();
		} else if (id.equals(MessageTemplate.CUSTOM_ACTION_PLAN_TASK_ID)) {
			messageParams = createCustomActionPlanTaskEmailMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID)) {
			messageParams = createEarlyAlertConfirmationToAdvisorMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_CONFIRMATIONTOFACULTY_ID)) {
			messageParams = createEarlyAlertConfirmationToFacultyMessageParams();
		} else if (id.equals(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID)) {
			messageParams = createEarlyAlertJournalEntryMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_RESPONSE_REQUIRED_ID)) {
			messageParams = createEarlyAlertResponseRequiredReminderToCoachMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_RESPONSETOFACULTYFROMCOACH_ID)) {
			messageParams = createEarlyAlertResponseToFacultyFromCoachMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_RESPONSE_TO_REFERRAL_SOURCE_ID)) {
			messageParams = createEarlyAlertResponseToReferralSourceFromCoachMessageParams();
		} else if (id.equals(MessageTemplate.EARLYALERT_MESSAGETOSTUDENT_ID)) {
			messageParams = createEarlyAlertSentToStudentMessageParams();
		} else if (id.equals(MessageTemplate.EMAIL_JOURNAL_ENTRY_ID)) {
			messageParams = createEmailJournalEntryMessageParams();
		} else if (id.equals(MessageTemplate.OUTPUT_MAP_PLAN_FULL_ID)) {
			messageParams = createMapPlanFullPrintoutMessageParams();
		} else if (id.equals(MessageTemplate.OUTPUT_MAP_PLAN_MATRIX_ID)) {
			messageParams = createMapPlanPrintoutMessageParams();
		} else if (id.equals(MessageTemplate.MAP_STATUS_REPORT_ID)) {
			messageParams = createMapStatusCalculationRunReportMessageParams();
		} else if (id.equals(MessageTemplate.MYGPS_WELCOME_MESSAGE_ID)) {
			messageParams = createMyGPSWelcomeMessageMessageParams();
		} else if (id.equals(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID)) {
			messageParams = createNewStudentIntakeTaskEmailMessageParams();
		} else if (id.equals(MessageTemplate.OUTPUT_TEMPLATE_PLAN_MATRIX_ID)) {
			messageParams = createTemplatePlanPrintoutMessageParams();
		} else if (id.equals(MessageTemplate.BULK_ADD_CASELOAD_REASSIGNMENT_ID)) {
			messageParams = createBulkAddCaseloadReassignmentMessageParams();
		} else{
			messageParams = new HashMap<String, Object>();
		}

		return populateFromTemplateAsMessageTempateTO(id, messageParams);
	}

	private Map<String, Object> createPreviewActionPlanEmailMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskTOs", MessageTemplatePreviewTOBuilder.createTaskTOList());
		messageParams.put("goalTOs", MessageTemplatePreviewTOBuilder.createGoalTOList());
		messageParams.put("strengthTOs", MessageTemplatePreviewTOBuilder.createStrengthTOList());
		messageParams.put("student", MessageTemplatePreviewTOBuilder.createPerson());
		messageParams.put("fullName", "Student FullName");
		return messageParams;
	}

	private Map<String,Object> createPreviewActionPlanStepEmailMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("task", MessageTemplatePreviewTOBuilder.createTaskMessageTemplateTO());
		messageParams.put("dueDateFormatted", formatDate(Calendar.getInstance().getTime()));
		return messageParams;
	}

	private Map<String,Object> createPreviewCoachingAssignmentChangeMessageMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("student", MessageTemplatePreviewTOBuilder.createStudentPersonLiteMessageTemplateTO());
		messageParams.put("oldCoach", MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO());
		messageParams.put("newCoach", MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO());
		messageParams.put("changedBy", MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO());
		return messageParams;
	}

	private Map<String,Object> createContactCoachEmailMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("subject", "This is the email subject");
		messageParams.put("message", "This is the body of the email");
		messageParams.put("student", MessageTemplatePreviewTOBuilder.createPerson());
		messageParams.put("fullName", "Student Fullname");
		return messageParams;
	}

	private Map<String,Object> createCustomActionPlanTaskEmailMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("task", MessageTemplatePreviewTOBuilder.createTaskMessageTemplateTO());
		messageParams.put("dueDateFormatted", formatDate(Calendar.getInstance().getTime()));
		return messageParams;
	}

	private Map<String,Object> createEarlyAlertMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("course", MessageTemplatePreviewTOBuilder.createFacultyCourse());
		messageParams.put("term", MessageTemplatePreviewTOBuilder.createTerm());
		messageParams.put("earlyAlert", MessageTemplatePreviewTOBuilder.createEarlyAlertMessageTemplateTO());
		messageParams.put("termToRepresentEarlyAlert",configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("TermToRepresentEarlyAlert",configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("termForEarlyAlert",configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("linkToSSP",configService.getByNameEmpty("serverExternalPath"));
		messageParams.put("applicationTitle",configService.getByNameEmpty("app_title"));
		messageParams.put("institutionName",configService.getByNameEmpty("inst_name"));
		messageParams.put("FirstName", "FirstName");
		messageParams.put("LastName", "LastName");
		messageParams.put("CourseName", "CourseName");
		return messageParams;
	}
	private Map<String,Object> createEarlyAlertConfirmationToAdvisorMessageParams() {
		return createEarlyAlertMessageParams();
	}

	private Map<String,Object> createEarlyAlertConfirmationToFacultyMessageParams() {
		return createEarlyAlertMessageParams();
	}

	private Map<String,Object> createEarlyAlertJournalEntryMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("course", MessageTemplatePreviewTOBuilder.createFacultyCourse());
		messageParams.put("term", MessageTemplatePreviewTOBuilder.createTerm());
		messageParams.put("earlyAlert", MessageTemplatePreviewTOBuilder.createEarlyAlertMessageTemplateTO());
		messageParams.put("termToRepresentEarlyAlert", configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("TermToRepresentEarlyAlert", configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("termForEarlyAlert",configService.getByNameEmpty("term_to_represent_early_alert"));
		messageParams.put("linkToSSP", configService.getByNameEmpty("serverExternalPath"));
		messageParams.put("applicationTitle", configService.getByNameEmpty("app_title"));
		messageParams.put("institutionName", configService.getByNameEmpty("inst_name"));
		messageParams.put("FirstName", "FirstName");
		messageParams.put("LastName", "LastName");
		messageParams.put("CourseName", "CourseName");
		messageParams.put("earlyAlertResponse", MessageTemplatePreviewTOBuilder.createEarlyAlertResponseMessageTemplateTO());
		messageParams.put("workPhone", "WorkPhone");
		messageParams.put("officeLocation", "officeLocation");
		return messageParams;
	}

	private Map<String,Object> createEarlyAlertResponseRequiredReminderToCoachMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("earlyAlertTOPairs", MessageTemplatePreviewTOBuilder.createEarlyAlertTOPairs());
		messageParams.put("coach", MessageTemplatePreviewTOBuilder.createPerson(false));
		messageParams.put("DateTimeUtils", DateTimeUtils.class);
		messageParams.put("termToRepresentEarlyAlert", configService.getByNameEmpty("term_to_represent_early_alert"));
		return messageParams;
	}

	private Map<String,Object> createEarlyAlertResponseToFacultyFromCoachMessageParams() {
		Map<String, Object> messageParams = createEarlyAlertMessageParams();
		messageParams.put("earlyAlert", MessageTemplatePreviewTOBuilder.createEarlyAlertMessageTemplateTO());
		messageParams.put("earlyAlertResponse", MessageTemplatePreviewTOBuilder.createEarlyAlertResponseMessageTemplateTO());
		messageParams.put("earlyAlertReferral", MessageTemplatePreviewTOBuilder.createEarlyAlertReferralTO(""));
		messageParams.put("earlyAlertOutcome", MessageTemplatePreviewTOBuilder.createEarlyAlertOutcomeMessageTemplateTO());
		return messageParams;
	}

	private Map<String,Object> createEarlyAlertResponseToReferralSourceFromCoachMessageParams() {
		Map<String, Object> messageParams = createEarlyAlertMessageParams();
		messageParams.put("earlyAlert", MessageTemplatePreviewTOBuilder.createEarlyAlertMessageTemplateTO());
		messageParams.put("earlyAlertResponse", MessageTemplatePreviewTOBuilder.createEarlyAlertResponseMessageTemplateTO());
		messageParams.put("earlyAlertReferral", MessageTemplatePreviewTOBuilder.createEarlyAlertReferralTO(""));
		messageParams.put("earlyAlertOutcome", MessageTemplatePreviewTOBuilder.createEarlyAlertOutcomeMessageTemplateTO());
		return messageParams;
	}

	private Map<String,Object> createEarlyAlertSentToStudentMessageParams() {
		return createEarlyAlertMessageParams();
	}

	private Map<String,Object> createEmailJournalEntryMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("message", MessageTemplatePreviewTOBuilder.createMessageTO());
		messageParams.put("messageContext", MessageTemplatePreviewTOBuilder.createMessageContextTO());
		return messageParams;
	}

	private Map<String,Object> createMapPlanFullPrintoutMessageParams() {
		final  Map<String, Object> messageParams = addParamsToMapPlan(
				MessageTemplatePreviewTOBuilder.createPerson("_student"),
				MessageTemplatePreviewTOBuilder.createPerson("_owner", false),
				MessageTemplatePreviewTOBuilder.createPerson("_lastModifiedBy", false),
				MessageTemplatePreviewTOBuilder.createPlanTO(),
				new BigDecimal(50),
				MessageTemplatePreviewTOBuilder.createTermCourses(),
				"InstitutionName",
				"These are the plan notes.");

		messageParams.put("isPrivate", false);
		messageParams.put("includeCourseDescription", true);
		messageParams.put("includeFinancialAidInformation", true);
		messageParams.put("includeHeaderFooter", true);
		messageParams.put("includeTotalTimeExpected",  true);
		messageParams.put("totalPlanDevHours", new BigDecimal(100));
		messageParams.put("planContactNotes", "These are the plan contact notes.");
		messageParams.put("planStudentNotes", "These are the plan student notes.");
		messageParams.put("contactEmail", "contact@email.edu");
		messageParams.put("contactPhone", "555-666-7777");
		messageParams.put("contactTitle", "Plan ContactTitle");
		messageParams.put("contactName", "Plan ContactName");
		messageParams.put("neededFor67PercentCompletion", new BigDecimal(25));
		messageParams.put("financialAidGpa", new BigDecimal(3.5));
		messageParams.put("hoursNeededB", new BigDecimal(30));
		messageParams.put("attemptedHours", new BigDecimal(20));
		messageParams.put("completedHours", new BigDecimal(18));
		messageParams.put("completionRage", new BigDecimal(90));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("programName", "ProgramName");
		messageParams.put("printParams",params);

		return messageParams;
	}

	private Map<String,Object> createMapPlanPrintoutMessageParams() {
		final Map<String, Object> messageParams = addParamsToMapPlan(
				MessageTemplatePreviewTOBuilder.createPerson("_student"),
				MessageTemplatePreviewTOBuilder.createPerson("_owner", false),
				MessageTemplatePreviewTOBuilder.createPerson("_lastModifiedBy", false),
				MessageTemplatePreviewTOBuilder.createPlanTO(),
				new BigDecimal(50),
				MessageTemplatePreviewTOBuilder.createTermCourses(),
				"InstitutionName",
				"These are the plan notes.");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("programName", "ProgramName");
		messageParams.put("printParams",params);
		return messageParams;
	}

	private Map<String,Object> createMapStatusCalculationRunReportMessageParams() {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy HH:mm:ss z");
		messageParams.put("startTime", sdf.format(Calendar.getInstance().getTime()));
		messageParams.put("endTime", sdf.format(Calendar.getInstance().getTime()));
		messageParams.put("totalPlans", 10);
		messageParams.put("title", "Map Status Report");
		messageParams.put("reportSummaryDetails", MessageTemplatePreviewTOBuilder.createMapStatusReportSummaryDetails());
		messageParams.put("termConfigReminder", false);
		messageParams.put("error", false);
		return messageParams;
	}

	private Map<String,Object> createMyGPSWelcomeMessageMessageParams() {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		setInstitutionValues(messageParams);
		return messageParams;
	}

	private Map<String,Object> createNewStudentIntakeTaskEmailMessageParams() {
		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskName", "Task Name");
		messageParams.put("student", MessageTemplatePreviewTOBuilder.createPerson());
		messageParams.put("fullName", "Student FullName");
		messageParams.put("description", "Task Description");
		messageParams.put("dueDateFormatted", formatDate(Calendar.getInstance().getTime()));
		return messageParams;
	}

	private Map<String,Object> createTemplatePlanPrintoutMessageParams() {
		final Map<String, Object> messageParams = addParamsToMapPlan(
				null,
				MessageTemplatePreviewTOBuilder.createPerson("_owner", false),
				MessageTemplatePreviewTOBuilder.createPerson("_lastModifiedBy", false),
				MessageTemplatePreviewTOBuilder.createPlanTO(),
				new BigDecimal(50),
				MessageTemplatePreviewTOBuilder.createTermCourses(),
				"InstitutionName",
				"These are the plan notes.");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departmentName", "PlanDepartmentName");
		params.put("divisionName", "PlanDivisionName");
		params.put("programName", "PlanProgramName");
		messageParams.put("printParams",params);
		return messageParams;
	}

	private Map<String,Object> createBulkAddCaseloadReassignmentMessageParams() {
		Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("successCount", 7);
		List<String> errors = new ArrayList<String>();
		errors.add("This is the first error.");
		errors.add("This is the second error.");
		errors.add("This is the third error.");
		messageParams.put("errors", errors);
		return messageParams;
	}

}