package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.DisabilityTypeDao;
import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.service.reference.DisabilityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisabilityTypeServiceImpl extends
		AbstractReferenceService<DisabilityType>
		implements DisabilityTypeService {

	@Autowired
	transient private DisabilityTypeDao dao;

	protected void setDao(final DisabilityTypeDao dao) {
		this.dao = dao;
	}

	@Override
	protected DisabilityTypeDao getDao() {
		return dao;
	}
}
