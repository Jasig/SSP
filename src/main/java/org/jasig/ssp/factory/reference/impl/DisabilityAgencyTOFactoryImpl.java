package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.DisabilityAgencyDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.DisabilityAgencyTOFactory;
import org.jasig.ssp.model.reference.DisabilityAgency;
import org.jasig.ssp.transferobject.reference.DisabilityAgencyTO;

@Service
@Transactional(readOnly = true)
public class DisabilityAgencyTOFactoryImpl extends
		AbstractReferenceTOFactory<DisabilityAgencyTO, DisabilityAgency>
		implements DisabilityAgencyTOFactory {

	public DisabilityAgencyTOFactoryImpl() {
		super(DisabilityAgencyTO.class, DisabilityAgency.class);
	}

	@Autowired
	private transient DisabilityAgencyDao dao;

	@Override
	protected DisabilityAgencyDao getDao() {
		return dao;
	}

}
