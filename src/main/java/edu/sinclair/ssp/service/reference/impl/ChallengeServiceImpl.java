package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ChallengeDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.ChallengeService;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeServiceImpl.class);

	@Autowired
	private ChallengeDao dao;

	@Override
	public List<Challenge> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public Challenge get(UUID id) throws ObjectNotFoundException {
		Challenge obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Challenge");
		}
		return obj;
	}

	@Override
	public Challenge create(Challenge obj) {
		return dao.save(obj);
	}

	@Override
	public Challenge save(Challenge obj) throws ObjectNotFoundException {
		Challenge current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Challenge current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ChallengeDao dao) {
		this.dao = dao;
	}

}
