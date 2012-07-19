package org.jasig.ssp.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Message;
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
	 * Send e-mail ({@link Message}) to the student.
	 * 
	 * <p>
	 * This method is not called during the {@link #create(EarlyAlert)} action.
	 * It must be called by the calling code.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 *             If reference data could not be found.
	 * @throws SendFailedException
	 *             If message send action failed.
	 * @throws ValidationException
	 *             If any data was invalid.
	 */
	public void sendMessageToStudent(@NotNull final EarlyAlert earlyAlert)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException;

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