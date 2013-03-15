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
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.TaskTO;
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

	private SubjectAndBody populateFromTemplate(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters) {
		try {
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
		final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy",
				Locale.getDefault());
		return format.format(date);
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
			final List<TaskTO> taskTOs, final List<GoalTO> goalTOs) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskTOs", taskTOs);
		messageParams.put("goalTOs", goalTOs);
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
}