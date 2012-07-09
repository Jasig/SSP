package org.jasig.ssp.service.reference.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		MessageTemplateService { // NOPMD

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageTemplateServiceImpl.class);

	@Autowired
	transient private MessageTemplateDao dao;

	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	@Autowired
	private transient PersonAttributesService personAttributeService;

	@Autowired
	private transient HttpServletRequest request;

	@Autowired
	private transient HttpServletResponse response;

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

			try {
				templateParameters.put("officeLocation", personAttributeService
						.getAttributes(request, response, "officeLocation"));
			} catch (final ObjectNotFoundException e) {
				LOGGER.info("Person attribute \"officeLocation\" could not be loaded.");
			}

			try {
				templateParameters.put("workPhone", personAttributeService
						.getAttributes(request, response, "workPhone"));
			} catch (final ObjectNotFoundException e) {
				LOGGER.info("Person attribute \"workPhone\" could not be loaded.");
			}

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

		messageParams.put("dueDateFormatted", formatDate(task.getDueDate()));

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
}