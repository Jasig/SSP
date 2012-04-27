package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideGroupDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.SelfHelpGuideGroupTOFactory;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideGroupTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideGroupTOFactoryImpl extends
		AbstractReferenceTOFactory<SelfHelpGuideGroupTO, SelfHelpGuideGroup>
		implements SelfHelpGuideGroupTOFactory {

	public SelfHelpGuideGroupTOFactoryImpl() {
		super(SelfHelpGuideGroupTO.class, SelfHelpGuideGroup.class);
	}

	@Autowired
	private SelfHelpGuideGroupDao dao;

	@Override
	protected SelfHelpGuideGroupDao getDao() {
		return dao;
	}

}
