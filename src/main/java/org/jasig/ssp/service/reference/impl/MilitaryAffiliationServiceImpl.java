package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.MilitaryAffiliationDao;
import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.service.reference.MilitaryAffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MilitaryAffiliationServiceImpl extends
		AbstractReferenceService<MilitaryAffiliation>
		implements MilitaryAffiliationService {

	@Autowired
	transient private MilitaryAffiliationDao dao;

	protected void setDao(final MilitaryAffiliationDao dao) {
		this.dao = dao;
	}

	@Override
	protected MilitaryAffiliationDao getDao() {
		return dao;
	}
}
