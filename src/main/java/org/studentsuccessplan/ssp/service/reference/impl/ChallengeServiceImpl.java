package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	private ChallengeDao dao;

	@Override
	public List<Challenge> getAll(final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final String sort, final String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public Challenge get(final UUID id) throws ObjectNotFoundException {
		Challenge obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Challenge");
		}

		return obj;
	}

	@Override
	public Challenge create(final Challenge obj) {
		return dao.save(obj);
	}

	@Override
	public Challenge save(final Challenge obj) throws ObjectNotFoundException {
		Challenge current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		Challenge current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(final ChallengeDao dao) {
		this.dao = dao;
	}
}
