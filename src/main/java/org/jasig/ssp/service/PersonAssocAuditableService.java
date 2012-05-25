package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonAssocAuditableService<T extends PersonAssocAuditable>
		extends AuditableCrudService<T> {
	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param person
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<T> getAllForPerson(Person person, SortingAndPaging sAndP);
}
