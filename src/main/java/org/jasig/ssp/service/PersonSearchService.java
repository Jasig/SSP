package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonSearchService {

	/**
	 * 
	 * @param programStatus
	 * @param outsideCaseload
	 * @param searchTerm
	 * @param sAndP
	 * @return
	 * @throws ObjectNotFoundException
	 *             if program status or other reference data passed does not
	 *             exist in the db.
	 */
	PagingWrapper<PersonSearchResult> searchBy(ProgramStatus programStatus,
			Boolean outsideCaseload, String searchTerm, Person advisor,
			SortingAndPaging sAndP)
			throws ObjectNotFoundException;
}
