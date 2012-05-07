package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.VeteranStatusDao;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VeteranStatus service implementation
 * 
 * @author daniel.bower
 */
@Service
@Transactional
public class VeteranStatusServiceImpl extends
		AbstractReferenceService<VeteranStatus>
		implements VeteranStatusService {

	@Autowired
	transient private VeteranStatusDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public VeteranStatusServiceImpl() {
		super();
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final VeteranStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected VeteranStatusDao getDao() {
		return dao;
	}
}
