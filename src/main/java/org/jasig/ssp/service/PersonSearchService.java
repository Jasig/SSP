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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.CaseLoadSearchTO;
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

	PagingWrapper<PersonSearchResult2> search2(PersonSearchRequest from);
	
	PagingWrapper<PersonSearchResult2> searchPersonDirectory(PersonSearchRequest from);
	
	void refreshDirectoryPerson();
	
	void refreshDirectoryPersonBlue();
	

	/**
	 * Gets the case load for the specified {@link CoachPersonLiteTO} (advisor)
	 * filtered by the specified program status, object status, sorting, and
	 * paging parameters.
	 * 
	 * @param programStatus
	 *            program status; optional — defaults to
	 *            {@link ProgramStatus#ACTIVE_ID}.
	 * @param coach
	 *            coach (advisor); required
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return the case load for the specified {@link CoachPersonLiteTO}
	 *         (advisor) filtered by the specified parameters
	 * @throws ObjectNotFoundException
	 *             If any referenced data could not be loaded.
	 * @throws ValidationException 
	 */
	PagingWrapper<PersonSearchResult2> caseLoadFor(ProgramStatus programStatus,
			Person coach, SortingAndPaging sAndP)
			throws ObjectNotFoundException;

	/**
	 * Count <em>current</em> caseloads for <em>all</em> current coaches. A
	 * "current" case is a student with any non-expired program status at this
	 * moment, i.e. when this method is invoked, where that student is also
	 * assigned to a coach. "All current coaches" means that coaches declared
	 * as such by {@link org.jasig.ssp.service.PersonAttributesService#getCoaches()}
	 * but having no caseload will be included in results. Also includes users
	 * who do have caseloads but who aren't designated as coaches by
	 * {@link org.jasig.ssp.service.PersonAttributesService#getCoaches()}.
	 *
	 * <p>This is effectivley the same as
	 * <code>caseLoadCountsByStatus(..., now(), null)</code>. This
	 * <em>usually</em> means a student will only be counted at most once.
	 * But this cannot be strictly guaranteed.</p>
	 *
	 * <p>Design note: The response is not paged because this method exists
	 * primarily to support reporting features, which we know currently need to
	 * read the entire report set into memory, there was no point in returning
	 * a paged response.</p>
	 *
	 * @param studentTypeIds filter the results to only count students having
	 *                       one of these types. <code>null</code> or empty
	 *                       collection is a wildcard
	 * @return
	 */
	Collection<CoachCaseloadRecordCountForProgramStatus>
		currentCaseloadCountsByStatus(CaseLoadSearchTO searchForm);

	/**
	 * Count caseloads overlapping the given date range for <em>all</em>
	 * current coaches. This is very similar to
	 * {@link #currentCaseloadCountsByStatus(java.util.List)} except that
	 * counted caseloads must have been/will be current during the given
	 * date range. I.e. they needn't be current when this method is executed.
	 *
	 * <p>Note that this method is technically counting <em>statuses</em>, not
	 * <em>students.</em> So every active status for a given student during
	 * the specified interval will increment the associated coach's count. I.e.
	 * students can be double (or triple or quadruple) counted.</p>
	 *
	 * <p>Also note that the current implementation cannot guarantee that
	 * the coach currently assigned to any given student is the same coach
	 * who was assigned to that student during the given date range. So the
	 * results are not necessarily reliable for tracking individual coach
	 * caseloads, especially if students are reassigned frequently.</p>
	 *
	 * @param studentTypeIds
	 * @param programStatusDateFrom status effectiveness lower bound, inclusive.
	 *                              null is a wildcard, i.e. "beginning of time".
	 *                              Statuses with effectiveness starting prior
	 *                              to this date will still be included - the
	 *                              effective range just needs to
	 *                              <em>intersect</em> the range from this to
	 *                              <code>programStatusDateTo</code>.
	 * @param programStatusDateTo status effectiveness upper bound, inclusive.
	 *                            null is a wildcard, i.e. "end of time".
	 *                            Statuses with effectiveness extending beyond
	 *                            this date will still be included - the
	 *                            effective range just needs to
	 *                            <em>intersect</em> the range from this to
	 *                            <code>programStatusDateFrom</code>
	 * @return
	 */
	Collection<CoachCaseloadRecordCountForProgramStatus>
		caseLoadCountsByStatus(List<UUID> studentTypeIds,
			Date programStatusDateFrom,
			Date programStatusDateTo,
			String homeDepartment);

	void reassignStudents(CaseloadReassignmentRequestTO obj) throws ObjectNotFoundException;
}