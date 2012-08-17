package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.ProgramStatusDao;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

	// Note that care currently needs to be taken to ensure this matches
	// up w/ a corresponding value in Constants.js
	private static final UUID ACTIVE_STATUS_UUID =
			UUID.fromString("b2d12527-5056-a51a-8054-113116baab88");

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

	@Override
	public ProgramStatus getActiveStatus() throws ObjectNotFoundException {
		return get(ACTIVE_STATUS_UUID);
	}
}