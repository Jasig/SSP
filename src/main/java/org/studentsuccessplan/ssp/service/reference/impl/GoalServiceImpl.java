package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.GoalDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.GoalService;

@Service
@Transactional
public class GoalServiceImpl implements GoalService {

	@Autowired
	private GoalDao dao;

	@Override
	public List<Goal> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public Goal get(UUID id) throws ObjectNotFoundException {
		Goal obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Goal");
		}

		return obj;
	}

	@Override
	public Goal create(Goal obj) {
		return dao.save(obj);
	}

	@Override
	public Goal save(Goal obj) throws ObjectNotFoundException {
		Goal current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setConfidentialityLevel(obj.getConfidentialityLevel());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Goal current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(GoalDao dao) {
		this.dao = dao;
	}
}
