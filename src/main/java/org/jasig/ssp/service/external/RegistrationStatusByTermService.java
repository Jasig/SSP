package org.jasig.ssp.service.external;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface RegistrationStatusByTermService extends
		ExternalDataService<RegistrationStatusByTerm> {

	RegistrationStatusByTerm getForTerm(Person person, Term term);

	/**
	 * 
	 * @throws ObjectNotFoundException
	 *             if current term does not exist.
	 */
	RegistrationStatusByTerm getForCurrentTerm(Person person)
			throws ObjectNotFoundException;

	PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			final Person person, final SortingAndPaging sAndP);

	Person applyRegistrationStatusForCurrentTerm(Person person);
}
