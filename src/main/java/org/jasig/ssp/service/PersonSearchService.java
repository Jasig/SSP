/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
	 * @param requireProgramStatus
	 *            implicitly <code>true</code> if <code>programStatus</code> is
	 *            non-null and defaults to <code>true</code>, else if set to
	 *            <code>false</code> can include persons with no status
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
			Boolean requireProgramStatus,
			Boolean outsideCaseload, String searchTerm, Person advisor,
			SortingAndPaging sAndP)
			throws ObjectNotFoundException, ValidationException;
}