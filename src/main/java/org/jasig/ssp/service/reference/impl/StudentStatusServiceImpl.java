package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.StudentStatusDao;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;

@Service
@Transactional
public class StudentStatusServiceImpl extends
		AbstractReferenceService<StudentStatus>
		implements StudentStatusService {

	public StudentStatusServiceImpl() {
		super(StudentStatus.class);
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
