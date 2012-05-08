package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.StudentStatusDao;
import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentStatusServiceImpl extends
		AbstractReferenceService<StudentStatus>
		implements StudentStatusService {

	public StudentStatusServiceImpl() {
		super();
	}

	@Autowired
	transient private StudentStatusDao dao;

	protected void setDao(final StudentStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected StudentStatusDao getDao() {
		return dao;
	}
}
