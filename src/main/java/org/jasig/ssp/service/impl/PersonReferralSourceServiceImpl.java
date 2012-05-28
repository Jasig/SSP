package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.PersonReferralSourceDao;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.PersonReferralSourceService;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonReferralSourceServiceImpl extends
		AbstractPersonAssocAuditableService<PersonReferralSource>
		implements
		PersonReferralSourceService {

	@Autowired
	private transient PersonReferralSourceDao dao;

	@Override
	protected PersonReferralSourceDao getDao() {
		return dao;
	}

}
