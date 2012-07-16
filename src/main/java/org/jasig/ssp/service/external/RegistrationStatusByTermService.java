package org.jasig.ssp.service.external;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;

public interface RegistrationStatusByTermService extends
		ExternalDataService<RegistrationStatusByTerm> {

	RegistrationStatusByTerm registeredForCurrentTerm(Person person);
}
