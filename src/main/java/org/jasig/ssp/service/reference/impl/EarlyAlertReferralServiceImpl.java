package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

import org.jasig.ssp.dao.reference.EarlyAlertReferralDao;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReferral service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertReferralServiceImpl extends
		AbstractReferenceService<EarlyAlertReferral>
		implements EarlyAlertReferralService {

	@Autowired
	transient private EarlyAlertReferralDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertReferralDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertReferralDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReferral load(final UUID id) {
		return dao.load(id);
	}
}
