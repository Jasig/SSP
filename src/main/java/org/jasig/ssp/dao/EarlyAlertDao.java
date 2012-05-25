package org.jasig.ssp.dao;

import org.jasig.ssp.model.EarlyAlert;
import org.springframework.stereotype.Repository;

/**
 * EarlyAlert data access methods
 * 
 * @author jon.adams
 * 
 */
@Repository
public class EarlyAlertDao extends
		AbstractPersonAssocAuditableCrudDao<EarlyAlert> implements
		PersonAssocAuditableCrudDao<EarlyAlert> {

	/**
	 * Construct a data access instance with specific class types for use by
	 * super class methods.
	 */
	protected EarlyAlertDao() {
		super(EarlyAlert.class);
	}
}
