package org.jasig.ssp.service;

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;

/**
 * Service that has restricted access to model data based on a
 * {@link ConfidentialityLevel} set on the model.
 */
public interface RestrictedPersonAssocPermissionService {

	void checkPermissionForModelGivesExcpetion(
			final RestrictedPersonAssocAuditable model, final SspUser requestor);

	/**
	 * Checks that the requester has permissions to view the model data per its
	 * specified {@link ConfidentialityLevel}.
	 * 
	 * @param model
	 *            the model
	 * @param requester
	 *            the current user requesting access to the model data
	 * @return True if the requester has permission to view the model data.
	 */
	boolean checkPermissionForModel(final RestrictedPersonAssocAuditable model,
			final SspUser requester);
}