package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.DisabilityAccommodationDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.DisabilityAccommodationTOFactory;
import org.jasig.ssp.model.reference.DisabilityAccommodation;
import org.jasig.ssp.transferobject.reference.DisabilityAccommodationTO;

@Service
@Transactional(readOnly = true)
public class DisabilityAccommodationTOFactoryImpl extends
		AbstractReferenceTOFactory<DisabilityAccommodationTO, DisabilityAccommodation>
		implements DisabilityAccommodationTOFactory {

	public DisabilityAccommodationTOFactoryImpl() {
		super(DisabilityAccommodationTO.class, DisabilityAccommodation.class);
	}

	@Autowired
	private transient DisabilityAccommodationDao dao;

	@Override
	protected DisabilityAccommodationDao getDao() {
		return dao;
	}

}
