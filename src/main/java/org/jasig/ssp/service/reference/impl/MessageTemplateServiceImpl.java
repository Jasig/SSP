package org.jasig.ssp.service.reference.impl;

import java.text.SimpleDateFormat;
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
		AbstractReferenceService<MessageTemplate>
		implements MessageTemplateService {

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

	private SubjectAndBody generate(final UUID id,
			final Map<String, Object> messageParams) {
		try {
			return populateFromTemplate(id, messageParams);
		} catch (ObjectNotFoundException e) {
			throw new ConfigException(id, ConfigException.TEMPLATE_TYPE, e);
		}
	}

	private SubjectAndBody populateFromTemplate(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException {

		final MessageTemplate messageTemplate = dao
				.get(messageTemplateId);

		final String subject = velocityTemplateService
				.generateContentFromTemplate(messageTemplate.getSubject(),
						messageTemplate.subjectTemplateId(), templateParameters);

		final String body = velocityTemplateService
				.generateContentFromTemplate(messageTemplate.getBody(),
						messageTemplate.bodyTemplateId(), templateParameters);

		return new SubjectAndBody(subject, body);
	}

	@Override
	public SubjectAndBody createContactCoachMessage(final String body,
			final String subject, final Person student) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("subject", subject);
		messageParams.put("body", body);
		messageParams.put("student", student);

		return generate(MessageTemplate.CONTACT_COACH_ID, messageParams);
	}

	@Override
	public SubjectAndBody createActionPlanStepMessage() {
		return generate(MessageTemplate.ACTION_PLAN_STEP_ID,
				new HashMap<String, Object>());
	}

	@Override
	public SubjectAndBody createCustomActionPlanTaskMessage() {
		return generate(MessageTemplate.CUSTOM_ACTION_PLAN_TASK_ID,
				new HashMap<String, Object>());
	}

	@Override
	public SubjectAndBody createActionPlanMessage(final Person student,
			final List<TaskTO> taskTOs) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskTOs", taskTOs);
		messageParams.put("student", student);

		return generate(MessageTemplate.ACTION_PLAN_EMAIL_ID, messageParams);
	}

	@Override
	public SubjectAndBody createStudentIntakeTaskMessage(final Task task) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("taskName", task.getName());
		messageParams.put("student", task.getPerson());

		// fix links in description
		final String linkedDescription = task.getDescription()
				.replaceAll(
						"href=\"/" + STUDENTUIPATH + "/",
						"href=\"" + getServerExternalPath() + "/"
								+ STUDENTUIPATH
								+ "/");
		messageParams.put("description", linkedDescription);

		final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy",
				Locale.getDefault());
		messageParams.put("dueDate",
				format.format(task.getDueDate()));

		return generate(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createJournalNoteForEarlyAlertResponseMessage(
			final String termToRepresentEarlyAlert, final EarlyAlert earlyAlert) {

		final Map<String, Object> messageParams = new HashMap<String, Object>();
		messageParams.put("termToRepresentEarlyAlert",
				termToRepresentEarlyAlert);
		messageParams.put("earlyAlert", earlyAlert);

		return generate(
				MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createAdvisorConfirmationForEarlyAlertMessage(
			final Map<String, Object> messageParams) {
		return generate(MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertToStudentMessage(
			final Map<String, Object> messageParams) {
		return generate(MessageTemplate.EARLYALERT_MESSAGETOSTUDENT_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertFacultyConfirmationMessage(
			final Map<String, Object> messageParams) {
		return generate(MessageTemplate.EARLYALERT_CONFIRMATIONTOFACULTY_ID,
				messageParams);
	}

	@Override
	public SubjectAndBody createEarlyAlertAdvisorConfirmationMessage(
			final Map<String, Object> messageParams) {
		return generate(MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID,
				messageParams);
	}
}