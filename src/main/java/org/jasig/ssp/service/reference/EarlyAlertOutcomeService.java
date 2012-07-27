package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertOutcome service
 * 
 * @author jon.adams
 */
public interface EarlyAlertOutcomeService extends
		ReferenceService<EarlyAlertOutcome> {

	/**
	 * Loads the specified instance from persistent storage.
	 * 
	 * @param id
	 *            identifier to load
	 * @return The specified instance
	 */
	EarlyAlertOutcome load(UUID id);

}
