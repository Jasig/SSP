package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.FundingSourceDao;
import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.service.reference.FundingSourceService;

@Service
@Transactional
public class FundingSourceServiceImpl extends
		AbstractReferenceService<FundingSource>
		implements FundingSourceService {

	public FundingSourceServiceImpl() {
		super(FundingSource.class);
	}

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
