package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertReferral service
 * 
 * @author jon.adams
 */
public interface EarlyAlertReferralService extends
		ReferenceService<EarlyAlertReferral> {

	/**
	 * Loads the specified instance from persistent storage.
	 * 
	 * @param id
	 *            identifier to load
	 * @return The specified instance
	 */
	EarlyAlertReferral load(UUID id);

}
