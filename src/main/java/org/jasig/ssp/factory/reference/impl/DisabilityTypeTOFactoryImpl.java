package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.DisabilityTypeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.DisabilityTypeTOFactory;
import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.transferobject.reference.DisabilityTypeTO;

@Service
@Transactional(readOnly = true)
public class DisabilityTypeTOFactoryImpl extends
		AbstractReferenceTOFactory<DisabilityTypeTO, DisabilityType>
		implements DisabilityTypeTOFactory {

	public DisabilityTypeTOFactoryImpl() {
		super(DisabilityTypeTO.class, DisabilityType.class);
	}

	@Autowired
	private transient DisabilityTypeDao dao;

	@Override
	protected DisabilityTypeDao getDao() {
		return dao;
	}

}
