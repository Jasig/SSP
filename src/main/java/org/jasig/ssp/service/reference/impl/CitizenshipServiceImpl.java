package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.CitizenshipDao;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CitizenshipServiceImpl extends
		AbstractReferenceService<Citizenship>
		implements CitizenshipService {

	@Autowired
	transient private CitizenshipDao dao;

	protected void setDao(final CitizenshipDao dao) {
		this.dao = dao;
	}

	@Override
	protected CitizenshipDao getDao() {
		return dao;
	}
}
