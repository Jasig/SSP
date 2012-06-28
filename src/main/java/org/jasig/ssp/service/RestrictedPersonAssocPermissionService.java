package org.jasig.ssp.service;

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;

public interface RestrictedPersonAssocPermissionService {

	void checkPermissionForModelGivesExcpetion(
			final RestrictedPersonAssocAuditable model, final SspUser requestor);

	boolean checkPermissionForModel(final RestrictedPersonAssocAuditable model,
			final SspUser requestor);

}
