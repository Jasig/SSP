package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.PersonServiceReasonDao;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.PersonServiceReasonService;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonServiceReasonServiceImpl extends
		AbstractPersonAssocAuditableService<PersonServiceReason>
		implements
		PersonServiceReasonService {

	@Autowired
	private transient PersonServiceReasonDao dao;

	@Override
	protected PersonServiceReasonDao getDao() {
		return dao;
	}

}
