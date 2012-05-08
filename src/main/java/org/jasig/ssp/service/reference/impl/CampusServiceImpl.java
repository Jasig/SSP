package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.CampusDao;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.reference.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Campus service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class CampusServiceImpl extends
		AbstractReferenceService<Campus>
		implements CampusService {

	@Autowired
	transient private CampusDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final CampusDao dao) {
		this.dao = dao;
	}

	@Override
	protected CampusDao getDao() {
		return dao;
	}
}
