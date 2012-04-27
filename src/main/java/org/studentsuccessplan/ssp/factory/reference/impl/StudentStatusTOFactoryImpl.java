package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.StudentStatusDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.StudentStatusTOFactory;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.transferobject.reference.StudentStatusTO;

@Service
@Transactional(readOnly = true)
public class StudentStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<StudentStatusTO, StudentStatus>
		implements StudentStatusTOFactory {

	public StudentStatusTOFactoryImpl() {
		super(StudentStatusTO.class, StudentStatus.class);
	}

	@Autowired
	private StudentStatusDao dao;

	@Override
	protected StudentStatusDao getDao() {
		return dao;
	}

}
