package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * CRUD DAO methods for entities with associated {@link Person} data and
 * {@link ConfidentialityLevel} data restrictions.
 * 
 * @param <T>
 *            An {@link Auditable} model that has a foreign key to both a
 *            {@link Person} and {@link ConfidentialityLevel}.
 */
public interface RestrictedPersonAssocAuditableDao<T extends RestrictedPersonAssocAuditable>
		extends PersonAssocAuditableCrudDao<T> {

	/**
	 * Get all data for the specified {@link Person}, but filtered by the
	 * requester's {@link ConfidentialityLevel} and the specified sorting and
	 * paging parameters.
	 * 
	 * @param personId
	 *            the {@link Person} data to load
	 * @param requestor
	 *            the user requesting the data, for {@link ConfidentialityLevel}
	 *            restrictions
	 * @param sAndP
	 *            sorting and paging parameters
	 * @return All data for the specified {@link Person}, filtered appropriately
	 */
	PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SspUser requestor,
			final SortingAndPaging sAndP);

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
}
