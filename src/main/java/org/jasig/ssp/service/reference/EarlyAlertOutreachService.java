package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertOutreach service
 * 
 * @author jon.adams
 */
public interface EarlyAlertOutreachService extends
		ReferenceService<EarlyAlertOutreach> {

	/**
	 * Loads the specified instance from persistent storage.
	 * 
	 * @param id
	 *            identifier to load
	 * @return The specified instance
	 */
	EarlyAlertOutreach load(UUID id);

}