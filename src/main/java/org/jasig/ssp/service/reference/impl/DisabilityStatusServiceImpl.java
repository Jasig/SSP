package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.DisabilityStatusDao;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisabilityStatusServiceImpl extends
		AbstractReferenceService<DisabilityStatus>
		implements DisabilityStatusService {

	@Autowired
	transient private DisabilityStatusDao dao;

	protected void setDao(final DisabilityStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected DisabilityStatusDao getDao() {
		return dao;
	}
}
