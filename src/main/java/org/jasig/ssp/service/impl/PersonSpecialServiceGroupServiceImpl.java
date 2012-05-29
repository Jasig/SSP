package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.PersonSpecialServiceGroupDao;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.PersonSpecialServiceGroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonSpecialServiceGroupServiceImpl extends
		AbstractPersonAssocAuditableService<PersonSpecialServiceGroup>
		implements
		PersonSpecialServiceGroupService {

	@Autowired
	private transient PersonSpecialServiceGroupDao dao;

	@Override
	protected PersonSpecialServiceGroupDao getDao() {
		return dao;
	}

}
