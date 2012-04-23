package org.studentsuccessplan.ssp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studentsuccessplan.ssp.dao.TaskDao;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityLevelDao;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.AbstractAuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

@Service
public class TaskServiceImpl extends AbstractAuditableCrudService<Task>
		implements TaskService {

	@Autowired
	private transient TaskDao dao;

	@Autowired
	private transient ConfidentialityLevelDao confidentialityLevelDao;

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	@Override
	public Task save(Task obj) throws ObjectNotFoundException {
		Task current = getDao().get(obj.getId());

		current.setChallenge(obj.getChallenge());
		current.setChallengeReferral(obj.getChallengeReferral());

		current.setCompletedDate(obj.getCompletedDate());
		current.setDescription(obj.getDescription());
		current.setDueDate(obj.getDueDate());
		current.setObjectStatus(obj.getObjectStatus());
		current.setPerson(obj.getPerson());
		current.setReminderSentDate(obj.getReminderSentDate());
		current.setSessionId(obj.getSessionId());

		if (obj.getConfidentialityLevel() == null) {
			current.setConfidentialityLevel(null);
		} else {
			current.setConfidentialityLevel(confidentialityLevelDao.load(obj
					.getConfidentialityLevel().getId()));
		}

		return getDao().save(current);
	}

	@Override
	public List<Task> getAllForPersonId(Person person, SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public List<Task> getAllForPersonId(Person person, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), complete, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(String sessionId,
			SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, complete, sAndP);
	}

	@Override
	public List<Task> getAllWhichNeedRemindersSent(SortingAndPaging sAndP) {
		return getDao().getAllWhichNeedRemindersSent(sAndP);
	}

	@Override
	public void markTaskComplete(Task task) {
		task.setCompletedDate(new Date());
		getDao().save(task);
	}

	@Override
	public void markTaskIncomplete(Task task) {
		task.setCompletedDate(null);
		getDao().save(task);
	}

	@Override
	public void markTaskCompletion(Task task, boolean complete) {
		if (complete) {
			markTaskComplete(task);
		} else {
			markTaskIncomplete(task);
		}
	}

	@Override
	public void setReminderSentDateToToday(Task task) {
		task.setReminderSentDate(new Date());
		getDao().save(task);
	}

	@Override
	public List<Task> getAllForPersonAndChallengeReferral(final Person person,
			final boolean complete, final ChallengeReferral challengeReferral,
			final SortingAndPaging sAndP) {
		return dao.getAllForPersonIdAndChallengeReferralId(person.getId(),
				complete, challengeReferral.getId(), sAndP);
	}

	@Override
	public List<Task> getAllForSessionIdAndChallengeReferral(
			final String sessionId, final boolean complete,
			final ChallengeReferral challengeReferral,
			final SortingAndPaging sAndP) {
		return dao.getAllForSessionIdAndChallengeReferralId(sessionId,
				complete, challengeReferral.getId(), sAndP);
	}

	@Override
	public Map<String, List<Task>> getAllGroupedByTaskGroup(Person person,
			SortingAndPaging sAndP) {
		Map<String, List<Task>> grouped = Maps.newTreeMap();
		List<Task> tasksForPerson = dao
				.getAllForPersonId(person.getId(), sAndP);
		for (Task task : tasksForPerson) {
			String group = task.getGroup();
			List<Task> tasksForGroup;
			if (!grouped.keySet().contains(group)) {
				tasksForGroup = new ArrayList<Task>();
				grouped.put(group, tasksForGroup);
			} else {
				tasksForGroup = grouped.get(group);
			}
			tasksForGroup.add(task);
		}
		return grouped;
	}
}
