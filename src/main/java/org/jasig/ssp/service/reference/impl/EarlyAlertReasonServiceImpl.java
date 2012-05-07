package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

import org.jasig.ssp.dao.reference.EarlyAlertReasonDao;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReason service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertReasonServiceImpl extends
		AbstractReferenceService<EarlyAlertReason>
		implements EarlyAlertReasonService {

	@Autowired
	transient private EarlyAlertReasonDao dao;

	/**
	 * Constructor that sets the specific class types to be used by base class
	 * methods.
	 */
	public EarlyAlertReasonServiceImpl() {
		super();
	}

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertReasonDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertReasonDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReason load(UUID id) {
		return dao.load(id);
	}
}
