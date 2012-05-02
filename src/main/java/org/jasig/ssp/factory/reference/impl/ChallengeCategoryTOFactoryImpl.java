package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ChallengeCategoryDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ChallengeCategoryTOFactory;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.transferobject.reference.ChallengeCategoryTO;

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