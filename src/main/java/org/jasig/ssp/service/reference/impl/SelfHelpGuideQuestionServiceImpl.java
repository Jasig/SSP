package org.jasig.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;

@Service
@Transactional
public class SelfHelpGuideQuestionServiceImpl extends
		AbstractReferenceService<SelfHelpGuideQuestion>
		implements SelfHelpGuideQuestionService {

	public SelfHelpGuideQuestionServiceImpl() {
		super(SelfHelpGuideQuestion.class);
	}

	@Autowired
	transient private SelfHelpGuideQuestionDao dao;

	protected void setDao(final SelfHelpGuideQuestionDao dao) {
		this.dao = dao;
	}

	@Override
	protected SelfHelpGuideQuestionDao getDao() {
		return dao;
	}
}
