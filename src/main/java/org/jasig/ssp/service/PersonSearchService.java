package org.jasig.ssp.service;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * PersonSearch service
 * 
 * @author jon.adams
 */
public interface PersonSearchService {
	/**
	 * Returns all Persons found for the specified filters
	 * 
	 * @param programStatus
	 *            the program status
	 * @param outsideCaseload
	 *            search outside case load
	 * @param searchTerm
	 *            the search term
	 * @param advisor
	 *            the advisor
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return All Persons found for the specified filters
	 * @throws ObjectNotFoundException
	 *             if program status or other reference data passed does not
	 *             exist in the database.
	 */
	PagingWrapper<PersonSearchResult> searchBy(ProgramStatus programStatus,
			Boolean outsideCaseload, String searchTerm, Person advisor,
			SortingAndPaging sAndP)
			throws ObjectNotFoundException, ValidationException;
}