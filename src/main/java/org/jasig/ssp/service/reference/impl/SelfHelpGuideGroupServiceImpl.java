package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideGroupDao;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideGroupService;

@Service
@Transactional
public class SelfHelpGuideGroupServiceImpl extends
		AbstractReferenceService<SelfHelpGuideGroup>
		implements SelfHelpGuideGroupService {

	public SelfHelpGuideGroupServiceImpl() {
		super(SelfHelpGuideGroup.class);
	}

	@Autowired
	transient private SelfHelpGuideGroupDao dao;

	protected void setDao(final SelfHelpGuideGroupDao dao) {
		this.dao = dao;
	}

	@Override
	protected SelfHelpGuideGroupDao getDao() {
		return dao;
	}
}
