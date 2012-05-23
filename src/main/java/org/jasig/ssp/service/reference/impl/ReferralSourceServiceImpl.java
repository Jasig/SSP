package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ReferralSourceDao;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReferralSourceServiceImpl extends
		AbstractReferenceService<ReferralSource>
		implements ReferralSourceService {

	@Autowired
	transient private ReferralSourceDao dao;

	protected void setDao(final ReferralSourceDao dao) {
		this.dao = dao;
	}

	@Override
	protected ReferralSourceDao getDao() {
		return dao;
	}
}
