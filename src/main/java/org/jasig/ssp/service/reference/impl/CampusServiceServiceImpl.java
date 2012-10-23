package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.CampusServiceDao;
import org.jasig.ssp.model.reference.CampusService;
import org.jasig.ssp.service.reference.CampusServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CampusServiceServiceImpl extends
		AbstractReferenceService<CampusService>
		implements CampusServiceService {

	@Autowired
	transient private CampusServiceDao dao;

	protected void setDao(final CampusServiceDao dao) {
		this.dao = dao;
	}

	@Override
	protected CampusServiceDao getDao() {
		return dao;
	}
}
