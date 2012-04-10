package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;

@Service
@Transactional
public class ChallengeReferralServiceImpl implements ChallengeReferralService {

	@Autowired
	private ChallengeReferralDao dao;

	@Override
	public List<ChallengeReferral> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public ChallengeReferral get(UUID id) throws ObjectNotFoundException {
		ChallengeReferral obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ChallengeReferral");
		}

		return obj;
	}

	@Override
	public ChallengeReferral create(ChallengeReferral obj) {
		return dao.save(obj);
	}

	@Override
	public ChallengeReferral save(ChallengeReferral obj) throws ObjectNotFoundException {
		ChallengeReferral current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ChallengeReferral current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ChallengeReferralDao dao) {
		this.dao = dao;
	}
}
