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

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertResponse service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertResponseService
		extends AuditableCrudService<EarlyAlertResponse> {
	/**
	 * Retrieve every response in the database filtered by the supplied status
	 * and early alert.
	 * 
	 * @param earlyAlert
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<EarlyAlertResponse> getAllForEarlyAlert(
			EarlyAlert earlyAlert,
			SortingAndPaging sAndP);

	Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom,
			Date createDateTo, List<UUID> studentTypeIds);

	Long getEarlyAlertRespondedToCount(Date createDateFrom,
			Date createDateTo, Campus campus);

	public Collection<EarlyAlertStudentOutreachReportTO> getEarlyAlertOutreachCountByOutcome(Date createDateFrom,
			Date createDateTo, List<UUID> outcomes, Person coach);
	
	
	/**
	 * Retrieves counts of Distinct Students and EarlyAlertResponses Grouped by Coaches
	 * Returned As EntityStudentCountByCoachTO
	 * @param coaches
	 * @param createDateFrom
	 * @param createDateTo
	 * @param studentTypeIds
	 * @param sAndP
	 * @return
	 */
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertResponseCountByCoaches(List<Person> coaches, 
			Date createDateFrom, 
			Date createDateTo,
			List<UUID> studentTypeIds, 
			SortingAndPaging sAndP);

	
	/**
	 * Gets a list of {@link Person} objects based on the specified criteria and
	 * {@link Early Alert Referral} identifiers.
	 * 
	 * @param earlyAlertReferralIds
	 *            list of {@link EarlyAlertReferral} early alert referral identifiers
	 *          
	 * @param addressLabelSearchTO
	 *            set of standard search criteria see peopleFromCriteria
	 * @param createForSingleSort
	 * @return A list of {@link Person} objects based on the specified criteria
	 *         and special service groups.
	 * @throws ObjectNotFoundException
	 *             If any of the special service groups could not be found.
	 */
	List<EarlyAlertStudentReportTO> getPeopleByEarlyAlertReferralIds(
			final List<UUID> earlyAlertReferralIds,
			final Date createDateFrom,
			final Date createDateTo, 
			final PersonSearchFormTO addressLabelSearchTO,
			final SortingAndPaging sAndP)throws ObjectNotFoundException;

}