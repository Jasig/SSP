package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.DisabilityAccommodationDao;
import org.jasig.ssp.model.reference.DisabilityAccommodation;
import org.jasig.ssp.service.reference.DisabilityAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisabilityAccommodationServiceImpl extends
		AbstractReferenceService<DisabilityAccommodation>
		implements DisabilityAccommodationService {

	@Autowired
	transient private DisabilityAccommodationDao dao;

	protected void setDao(final DisabilityAccommodationDao dao) {
		this.dao = dao;
	}

	@Override
	protected DisabilityAccommodationDao getDao() {
		return dao;
	}
}
