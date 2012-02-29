package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ChallengeDao;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
@Transactional
public class ChallengeServiceImpl implements ReferenceService<Challenge>, ChallengeService {

	private static final Logger logger = LoggerFactory.getLogger(ChallengeServiceImpl.class);

	@Autowired
	private ChallengeDao dao;

	@Override
	public List<Challenge> getAll() {
		return dao.getAll();
	}

	@Override
	public Challenge get(UUID id) {
		return dao.get(id);
	}

	@Override
	public Challenge save(Challenge obj) {
		return dao.save(obj);
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
		Challenge obj = dao.get(id);
		if(null!=obj){
			dao.delete(obj);
		}
	}

	protected void setDao(ChallengeDao dao){
		this.dao = dao;
	}

}
