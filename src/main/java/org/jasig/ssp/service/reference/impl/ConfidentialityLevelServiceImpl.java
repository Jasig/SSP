package org.jasig.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;

@Service
@Transactional
public class ConfidentialityLevelServiceImpl extends
		AbstractReferenceService<ConfidentialityLevel>
		implements ConfidentialityLevelService {

	public ConfidentialityLevelServiceImpl() {
		super(ConfidentialityLevel.class);
	}

	@Autowired
	transient private ConfidentialityLevelDao dao;

	protected void setDao(final ConfidentialityLevelDao dao) {
		this.dao = dao;
	}

	@Override
	protected ConfidentialityLevelDao getDao() {
		return dao;
	}
}
