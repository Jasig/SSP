package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.TaskDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.TaskTOFactory;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.TaskTO;

@Service
@Transactional(readOnly = true)
public class TaskTOFactoryImpl extends
		AbstractAuditableTOFactory<TaskTO, Task>
		implements TaskTOFactory {

	public TaskTOFactoryImpl() {
		super(TaskTO.class, Task.class);
	}

	@Autowired
	private TaskDao dao;

	@Autowired
	private PersonService personService;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private ChallengeReferralService challengeReferralService;

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	@Override
	public Task from(TaskTO tObject)
			throws ObjectNotFoundException {
		Task model = super.from(tObject);

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());
		model.setDeletable(tObject.isDeletable());
		model.setDueDate(tObject.getDueDate());
		model.setCompletedDate(tObject.getCompletedDate());
		model.setReminderSentDate(tObject.getReminderSentDate());

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		if (tObject.getChallengeId() != null) {
			model.setChallenge(challengeService.get(tObject.getChallengeId()));
		}

		if (tObject.getChallengeReferralId() != null) {
			model.setChallengeReferral(challengeReferralService.get(tObject
					.getChallengeReferralId()));
		}

		return model;
	}
}
