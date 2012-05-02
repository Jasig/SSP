package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChildCareArrangementDao;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;

@Service
@Transactional
public class ChildCareArrangementServiceImpl extends
		AbstractReferenceService<ChildCareArrangement>
		implements ChildCareArrangementService {

	public ChildCareArrangementServiceImpl() {
		super(ChildCareArrangement.class);
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
