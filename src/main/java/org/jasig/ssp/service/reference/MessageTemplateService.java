package org.jasig.ssp.service.reference;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * MessageTemplate service
 */
public interface MessageTemplateService extends
		AuditableCrudService<MessageTemplate> {

	@Override
	PagingWrapper<MessageTemplate> getAll(SortingAndPaging sAndP);

	@Override
	MessageTemplate get(UUID id) throws ObjectNotFoundException;

	@Override
	MessageTemplate create(MessageTemplate obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	MessageTemplate save(MessageTemplate obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	SubjectAndBody createContactCoachMessage(String body, String subject,
			Person student);

	SubjectAndBody createActionPlanStepMessage(final Task task);

	SubjectAndBody createCustomActionPlanTaskMessage(final Task task);

	SubjectAndBody createActionPlanMessage(Person student, List<TaskTO> taskTOs);

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