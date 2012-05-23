package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.SpecialServiceGroupDao;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpecialServiceGroupServiceImpl extends
		AbstractReferenceService<SpecialServiceGroup>
		implements SpecialServiceGroupService {

	@Autowired
	transient private SpecialServiceGroupDao dao;

	protected void setDao(final SpecialServiceGroupDao dao) {
		this.dao = dao;
	}

	@Override
	protected SpecialServiceGroupDao getDao() {
		return dao;
	}
}
