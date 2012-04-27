package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.FundingSourceDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.FundingSourceTOFactory;
import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.transferobject.reference.FundingSourceTO;

@Service
@Transactional(readOnly = true)
public class FundingSourceTOFactoryImpl extends
		AbstractReferenceTOFactory<FundingSourceTO, FundingSource>
		implements FundingSourceTOFactory {

	public FundingSourceTOFactoryImpl() {
		super(FundingSourceTO.class, FundingSource.class);
	}

	@Autowired
	private transient FundingSourceDao dao;

	@Override
	protected FundingSourceDao getDao() {
		return dao;
	}

}
