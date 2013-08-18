package org.jasig.ssp.service.security.oauth2;


import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface OAuth2ClientService extends AuditableCrudService<OAuth2Client> {
	/**
	 * Preferred over {@link #create(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunity to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	OAuth2Client create(OAuth2ClientTO obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Preferred over {@link #save(org.jasig.ssp.model.Auditable)} since it
	 * ensures the service impl has the opportunoty to implement all relevant
	 * validations.
	 *
	 * @param obj
	 * @return
	 */
	OAuth2Client save(OAuth2ClientTO obj) throws ObjectNotFoundException;
}
