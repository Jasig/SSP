package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SelfHelpGuideGroupDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideGroupTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideGroupTO;

@Service
@Transactional(readOnly = true)
public class SelfHelpGuideGroupTOFactoryImpl extends
		AbstractReferenceTOFactory<SelfHelpGuideGroupTO, SelfHelpGuideGroup>
		implements SelfHelpGuideGroupTOFactory {

	public SelfHelpGuideGroupTOFactoryImpl() {
		super(SelfHelpGuideGroupTO.class, SelfHelpGuideGroup.class);
	}

	@Autowired
	private transient SelfHelpGuideGroupDao dao;

	@Override
	protected SelfHelpGuideGroupDao getDao() {
		return dao;
	}

}
