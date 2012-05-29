package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.StudentTypeDao;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StudentType service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class StudentTypeServiceImpl extends
		AbstractReferenceService<StudentType>
		implements StudentTypeService {

	@Autowired
	transient private StudentTypeDao dao;

	@Override
	protected StudentTypeDao getDao() {
		return dao;
	}

	/**
	 * Sets the data access object
	 * 
	 * @param dao
	 *            the data access object
	 */
	protected void setDao(final StudentTypeDao dao) {
		this.dao = dao;
	}
}