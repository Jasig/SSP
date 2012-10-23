package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.DisabilityAgencyDao;
import org.jasig.ssp.model.reference.DisabilityAgency;
import org.jasig.ssp.service.reference.DisabilityAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisabilityAgencyServiceImpl extends
		AbstractReferenceService<DisabilityAgency>
		implements DisabilityAgencyService {

	@Autowired
	transient private DisabilityAgencyDao dao;

	protected void setDao(final DisabilityAgencyDao dao) {
		this.dao = dao;
	}

	@Override
	protected DisabilityAgencyDao getDao() {
		return dao;
	}
}
