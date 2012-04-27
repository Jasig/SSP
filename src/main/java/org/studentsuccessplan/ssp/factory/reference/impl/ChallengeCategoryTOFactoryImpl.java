package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeCategoryDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChallengeCategoryTOFactory;
import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeCategoryTO;

@Service
@Transactional(readOnly = true)
public class ChallengeCategoryTOFactoryImpl extends
		AbstractReferenceTOFactory<ChallengeCategoryTO, ChallengeCategory>
		implements ChallengeCategoryTOFactory {

	public ChallengeCategoryTOFactoryImpl() {
		super(ChallengeCategoryTO.class, ChallengeCategory.class);
	}

	@Autowired
	private transient ChallengeCategoryDao dao;

	@Override
	protected ChallengeCategoryDao getDao() {
		return dao;
	}

}