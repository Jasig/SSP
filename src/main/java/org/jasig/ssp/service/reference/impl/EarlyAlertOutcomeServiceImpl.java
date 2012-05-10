package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

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

	@Override
	public EarlyAlertOutcome load(final UUID id) {
		return dao.load(id);
	}
}
