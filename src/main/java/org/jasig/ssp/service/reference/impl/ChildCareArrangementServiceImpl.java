package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ChildCareArrangementDao;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChildCareArrangementServiceImpl extends
		AbstractReferenceService<ChildCareArrangement>
		implements ChildCareArrangementService {

	public ChildCareArrangementServiceImpl() {
		super();
	}

	@Autowired
	transient private ChildCareArrangementDao dao;

	protected void setDao(final ChildCareArrangementDao dao) {
		this.dao = dao;
	}

	@Override
	protected ChildCareArrangementDao getDao() {
		return dao;
	}
}
