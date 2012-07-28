package org.jasig.ssp.service.reference;

import java.util.List;
import java.util.Map;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.transferobject.GoalTO;
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
}