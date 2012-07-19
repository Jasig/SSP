package org.jasig.ssp.service.external;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * RegistrationStatusByTerm service
 */
public interface RegistrationStatusByTermService extends
		ExternalDataService<RegistrationStatusByTerm> {

	RegistrationStatusByTerm getForTerm(@NotNull Person person,
			@NotNull Term term);

	/**
	 * Gets all registration statuses for the current term.
	 * 
	 * @param person
	 *            the person
	 * @return All registration statuses for the current term.
	 * @throws ObjectNotFoundException
	 *             if current term does not exist.
	 */
	RegistrationStatusByTerm getForCurrentTerm(@NotNull Person person)
			throws ObjectNotFoundException;

	PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			@NotNull final Person person, final SortingAndPaging sAndP);

	Person applyRegistrationStatusForCurrentTerm(@NotNull Person person);
}