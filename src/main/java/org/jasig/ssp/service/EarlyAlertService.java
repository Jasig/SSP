package org.jasig.ssp.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * EarlyAlert service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertService
		extends PersonAssocAuditableService<EarlyAlert> {

	/**
	 * Create a new EarlyAlert, assign to the appropriate coordinator, e-mail
	 * all notifications and alerts, and save to persistent storage.
	 * 
	 * @param obj
	 *            EarlyAlert data
	 * @return The updated data object instance.
	 */
	@Override
	EarlyAlert create(EarlyAlert obj) throws ObjectNotFoundException,
			ValidationException;

	Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			final Collection<UUID> peopleIds);

	/**
	 * Fills early alert parameters for messages.
	 * 
	 * <p>
	 * Also used by early alert response since messages have similar template
	 * parameters.
	 * 
	 * @param earlyAlert
	 * @return Map of template parameters
	 */
	Map<String, Object> fillTemplateParameters(final EarlyAlert earlyAlert);
}