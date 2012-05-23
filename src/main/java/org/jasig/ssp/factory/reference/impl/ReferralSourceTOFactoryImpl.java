package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ReferralSourceDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ReferralSourceTOFactory;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.transferobject.reference.ReferralSourceTO;

@Service
@Transactional(readOnly = true)
public class ReferralSourceTOFactoryImpl extends
		AbstractReferenceTOFactory<ReferralSourceTO, ReferralSource>
		implements ReferralSourceTOFactory {

	public ReferralSourceTOFactoryImpl() {
		super(ReferralSourceTO.class, ReferralSource.class);
	}

	@Autowired
	private transient ReferralSourceDao dao;

	@Override
	protected ReferralSourceDao getDao() {
		return dao;
	}

}
