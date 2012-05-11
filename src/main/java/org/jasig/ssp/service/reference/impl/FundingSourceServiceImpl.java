package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.FundingSourceDao;
import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.service.reference.FundingSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FundingSourceServiceImpl extends
		AbstractReferenceService<FundingSource>
		implements FundingSourceService {

	@Autowired
	transient private FundingSourceDao dao;

	protected void setDao(final FundingSourceDao dao) {
		this.dao = dao;
	}

	@Override
	protected FundingSourceDao getDao() {
		return dao;
	}
}
