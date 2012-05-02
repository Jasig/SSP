package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.StudentStatusDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.StudentStatusTOFactory;
import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.transferobject.reference.StudentStatusTO;

@Service
@Transactional(readOnly = true)
public class StudentStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<StudentStatusTO, StudentStatus>
		implements StudentStatusTOFactory {

	public StudentStatusTOFactoryImpl() {
		super(StudentStatusTO.class, StudentStatus.class);
	}

	@Autowired
	private transient StudentStatusDao dao;

	@Override
	protected StudentStatusDao getDao() {
		return dao;
	}

}
