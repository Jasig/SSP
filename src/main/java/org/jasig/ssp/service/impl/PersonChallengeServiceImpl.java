package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.PersonChallengeDao;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.PersonChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonChallengeServiceImpl extends
		AbstractPersonAssocAuditableService<PersonChallenge> implements
		PersonChallengeService {

	@Autowired
	private transient PersonChallengeDao dao;

	@Override
	protected PersonChallengeDao getDao() {
		return dao;
	}

}
