package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertReason service
 * 
 * @author jon.adams
 */
public interface EarlyAlertReasonService extends
		ReferenceService<EarlyAlertReason> {

	/**
	 * Lazily load an instance for the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 * @return A lazily-loaded instance for the specified identifier.
	 */
	EarlyAlertReason load(UUID id);

}
