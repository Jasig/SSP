package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeCategoryDao;
import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.service.reference.ChallengeCategoryService;

@Service
@Transactional
public class ChallengeCategoryServiceImpl extends
		AbstractReferenceService<ChallengeCategory>
		implements ChallengeCategoryService {

	@Autowired
	transient private ChallengeCategoryDao dao;

	public ChallengeCategoryServiceImpl() {
		super(ChallengeCategory.class);
	}

	protected void setDao(final ChallengeCategoryDao dao) {
		this.dao = dao;
	}

	@Override
	protected ChallengeCategoryDao getDao() {
		return dao;
	}
}
