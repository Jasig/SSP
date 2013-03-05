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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * JournalEntry service
 */
public interface JournalEntryService
		extends RestrictedPersonAssocAuditableService<JournalEntry> {

	public Long getCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);

	public Long getStudentCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);  
	
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentJournalCountForCoaches(List<Person> coaches, 
			Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds, List<UUID> serviceReasonIds, SortingAndPaging sAndP);

}