package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideTOFactoryImpl extends
		AbstractReferenceTOFactory<SelfHelpGuideTO, SelfHelpGuide>
		implements SelfHelpGuideTOFactory {

	public SelfHelpGuideTOFactoryImpl() {
		super(SelfHelpGuideTO.class, SelfHelpGuide.class);
	}

	@Autowired
	private transient SelfHelpGuideDao dao;

	@Override
	protected SelfHelpGuideDao getDao() {
		return dao;
	}

}
