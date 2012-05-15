package org.jasig.ssp.service;

import org.jasig.ssp.model.EarlyAlert;

/**
 * EarlyAlert service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertService
		extends PersonAssocService<EarlyAlert> {

	/**
	 * Create a new EarlyAlert, assign to the appropriate coordinator, e-mail
	 * all notifications and alerts, and save to persistent storage.
	 * 
	 * @param obj
	 *            EarlyAlert data
	 * @return The updated data object instance.
	 */
	@Override
	EarlyAlert create(EarlyAlert obj) throws ObjectNotFoundException;
}