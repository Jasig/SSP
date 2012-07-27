package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.ReferenceService;

/**
 * EarlyAlertSuggestion service
 * 
 * @author jon.adams
 */
public interface EarlyAlertSuggestionService extends
		ReferenceService<EarlyAlertSuggestion> {

	/**
	 * Lazily load an instance for the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 * @return A lazily-loaded instance for the specified identifier.
	 */
	EarlyAlertSuggestion load(UUID id);

}
