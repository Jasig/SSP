package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideQuestionTOFactoryImpl
		extends
		AbstractReferenceTOFactory<SelfHelpGuideQuestionTO, SelfHelpGuideQuestion>
		implements SelfHelpGuideQuestionTOFactory {

	public SelfHelpGuideQuestionTOFactoryImpl() {
		super(SelfHelpGuideQuestionTO.class, SelfHelpGuideQuestion.class);
	}

	@Autowired
	private transient SelfHelpGuideQuestionDao dao;

	@Override
	protected SelfHelpGuideQuestionDao getDao() {
		return dao;
	}

}
