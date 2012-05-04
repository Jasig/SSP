package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.EarlyAlertOutcomeDao;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertOutcome service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertOutcomeServiceImpl extends
		AbstractReferenceService<EarlyAlertOutcome>
		implements EarlyAlertOutcomeService {

	@Autowired
	transient private EarlyAlertOutcomeDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public EarlyAlertOutcomeServiceImpl() {
		super(EarlyAlertOutcome.class);
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertOutcomeDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertOutcomeDao getDao() {
		return dao;
	}
}
