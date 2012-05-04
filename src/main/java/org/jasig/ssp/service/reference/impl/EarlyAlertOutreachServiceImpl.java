package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.EarlyAlertOutreachDao;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertOutreach service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertOutreachServiceImpl extends
		AbstractReferenceService<EarlyAlertOutreach>
		implements EarlyAlertOutreachService {

	@Autowired
	transient private EarlyAlertOutreachDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public EarlyAlertOutreachServiceImpl() {
		super(EarlyAlertOutreach.class);
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertOutreachDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertOutreachDao getDao() {
		return dao;
	}
}
