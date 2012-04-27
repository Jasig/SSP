package org.studentsuccessplan.ssp.service;

import java.util.List;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface PersonAssocService<T> extends AuditableCrudService<T> {
	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param person
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	List<T> getAllForPerson(Person person, SortingAndPaging sAndP);
}
