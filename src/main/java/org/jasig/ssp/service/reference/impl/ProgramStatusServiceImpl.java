package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ProgramStatusDao;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProgramStatus service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class ProgramStatusServiceImpl extends
		AbstractReferenceService<ProgramStatus>
		implements ProgramStatusService {

	@Autowired
	transient private ProgramStatusDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final ProgramStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected ProgramStatusDao getDao() {
		return dao;
	}
}