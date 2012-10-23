package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.DisabilityStatusDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.DisabilityStatusTOFactory;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.transferobject.reference.DisabilityStatusTO;

@Service
@Transactional(readOnly = true)
public class DisabilityStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<DisabilityStatusTO, DisabilityStatus>
		implements DisabilityStatusTOFactory {

	public DisabilityStatusTOFactoryImpl() {
		super(DisabilityStatusTO.class, DisabilityStatus.class);
	}

	@Autowired
	private transient DisabilityStatusDao dao;

	@Override
	protected DisabilityStatusDao getDao() {
		return dao;
	}

}
