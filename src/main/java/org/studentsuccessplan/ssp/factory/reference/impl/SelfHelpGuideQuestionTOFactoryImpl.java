package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideQuestionTO;

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
	private SelfHelpGuideQuestionDao dao;

	@Override
	protected SelfHelpGuideQuestionDao getDao() {
		return dao;
	}

}
