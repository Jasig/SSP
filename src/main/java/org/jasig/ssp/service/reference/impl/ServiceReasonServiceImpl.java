package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ServiceReasonDao;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceReasonServiceImpl extends
		AbstractReferenceService<ServiceReason>
		implements ServiceReasonService {

	@Autowired
	transient private ServiceReasonDao dao;

	protected void setDao(final ServiceReasonDao dao) {
		this.dao = dao;
	}

	@Override
	protected ServiceReasonDao getDao() {
		return dao;
	}
}
