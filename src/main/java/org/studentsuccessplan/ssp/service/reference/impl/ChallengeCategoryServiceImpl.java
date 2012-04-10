package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ChallengeCategoryDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ChallengeCategory;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.ChallengeCategoryService;

@Service
@Transactional
public class ChallengeCategoryServiceImpl implements ChallengeCategoryService {

	@Autowired
	private ChallengeCategoryDao dao;

	@Override
	public List<ChallengeCategory> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public ChallengeCategory get(UUID id) throws ObjectNotFoundException {
		ChallengeCategory obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "ChallengeCategory");
		}

		return obj;
	}

	@Override
	public ChallengeCategory create(ChallengeCategory obj) {
		return dao.save(obj);
	}

	@Override
	public ChallengeCategory save(ChallengeCategory obj) throws ObjectNotFoundException {
		ChallengeCategory current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		ChallengeCategory current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(ChallengeCategoryDao dao) {
		this.dao = dao;
	}
}
