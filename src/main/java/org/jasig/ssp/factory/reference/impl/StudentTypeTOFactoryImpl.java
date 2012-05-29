package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.StudentTypeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.StudentTypeTOFactory;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.transferobject.reference.StudentTypeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StudentType transfer object factory implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional(readOnly = true)
public class StudentTypeTOFactoryImpl
		extends
		AbstractReferenceTOFactory<StudentTypeTO, StudentType>
		implements StudentTypeTOFactory {

	public StudentTypeTOFactoryImpl() {
		super(StudentTypeTO.class,
				StudentType.class);
	}

	@Autowired
	private transient StudentTypeDao dao;

	@Override
	protected StudentTypeDao getDao() {
		return dao;
	}
}