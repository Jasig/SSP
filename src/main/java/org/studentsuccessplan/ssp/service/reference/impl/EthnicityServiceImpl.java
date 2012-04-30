package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.EthnicityDao;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;

@Service
@Transactional
public class EthnicityServiceImpl extends
		AbstractReferenceService<Ethnicity>
		implements EthnicityService {

	public EthnicityServiceImpl() {
		super(Ethnicity.class);
	}

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
