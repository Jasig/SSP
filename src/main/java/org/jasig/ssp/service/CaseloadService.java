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

import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Case load service
 */  
public interface CaseloadService {
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
	 */
	PagingWrapper<CaseloadRecord> caseLoadFor(ProgramStatus programStatus,
			Person coach, SortingAndPaging sAndP)
			throws ObjectNotFoundException;
	
	Long caseLoadCountFor(ProgramStatus programStatus,
			Person coach, List<UUID> studentTypeIds, Date programStatusDateFrom, Date programStatusDateTo) throws ObjectNotFoundException;

	Collection<CoachCaseloadRecordCountForProgramStatus>
		caseLoadCountsByStatusIncludingAllCurrentCoaches(
			List<UUID> studentTypeIds,
			Date programStatusDateFrom,
			Date programStatusDateTo);
}