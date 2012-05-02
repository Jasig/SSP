package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;

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
