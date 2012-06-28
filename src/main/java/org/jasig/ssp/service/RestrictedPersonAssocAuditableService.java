package org.jasig.ssp.service;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface RestrictedPersonAssocAuditableService<T extends RestrictedPersonAssocAuditable>
		extends PersonAssocAuditableService<T> {

	/**
	 * Retrieves the specified instances from persistent storage.
	 * 
	 * @param ids
	 *            the identifiers of the entities to load
	 * @param requester
	 *            user requesting the data, for restricting by
	 *            {@link ConfidentialityLevel}
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return Specified entities, filtered by the specified parameters, or
	 *         empty List if null
	 */
	List<T> get(final List<UUID> ids, final SspUser requester,
			final SortingAndPaging sAndP);

	PagingWrapper<T> getAllForPerson(Person person,
			SspUser requestor,
			SortingAndPaging sAndP);

	@Override
	@Deprecated
	PagingWrapper<T> getAllForPerson(Person person,
			SortingAndPaging sAndP);
}
