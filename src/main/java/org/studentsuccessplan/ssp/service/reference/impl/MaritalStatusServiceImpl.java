package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.MaritalStatusDao;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;

@Service
@Transactional
public class MaritalStatusServiceImpl extends
		AbstractReferenceService<MaritalStatus>
		implements MaritalStatusService {

	public MaritalStatusServiceImpl() {
		super(MaritalStatus.class);
	}

	@Autowired
	transient private MaritalStatusDao dao;

	protected void setDao(final MaritalStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected MaritalStatusDao getDao() {
		return dao;
	}
}
