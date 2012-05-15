package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.EthnicityDao;
import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.service.reference.EthnicityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EthnicityServiceImpl extends
		AbstractReferenceService<Ethnicity>
		implements EthnicityService {

	@Autowired
	transient private EthnicityDao dao;

	protected void setDao(final EthnicityDao dao) {
		this.dao = dao;
	}

	@Override
	protected EthnicityDao getDao() {
		return dao;
	}
}
