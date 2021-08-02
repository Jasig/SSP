/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertResponseCounts;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentResponseOutcomeReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * EarlyAlertResponse service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertResponseService
		extends AuditableCrudService<EarlyAlertResponse> {

	/**
	 * Same as {@link EarlyAlertResponseService#create(org.jasig.ssp.model.Auditable)}
	 * but accepts a TO representation of the entity to be created. This is
	 * the preferred way to create an {@link org.jasig.ssp.model.EarlyAlertResponse}.
	 *
	 * @param obj The EarlyAlertResponseTO transfer object
	 * @return EarlyAlertResponse
	 * @throws ValidationException
	 * 			If EA Response object is not valid
	 */
	EarlyAlertResponse create(EarlyAlertResponseTO obj) throws ValidationException;

	/**
	 * Retrieve every response in the database filtered by the supplied status
	 * and early alert.
	 * 
	 * @param earlyAlert
	 * 			  The EarlyAlert model
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<EarlyAlertResponse> getAllForEarlyAlert(
			EarlyAlert earlyAlert,
			SortingAndPaging sAndP);

	Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom,
			Date createDateTo, List<UUID> studentTypeIds);

	Long getRespondedToEarlyAlertCountForResponseCreatedDateRange(Date createDateFrom,
																  Date createDateTo, Campus campus, String rosterStatus);

	Long getRespondedToEarlyAlertCountForEarlyAlertCreatedDateRange(String termCode, Date startDate,
																	Date endDate, Campus campus, String rosterStatus);

	public Collection<EarlyAlertStudentOutreachReportTO> getEarlyAlertOutreachCountByOutcome(String alertTermCode,
         Date alertCreateDateFrom, Date alertCreateDateTo, Date responseCreateDateFrom, Date responseCreateDateTo,
         List<UUID> outcomes, String rosterStatus, Person coach);
	
	
	/**
	 * Retrieves counts of Distinct Students and EarlyAlertResponses Grouped by Coaches
	 * Returned As EntityStudentCountByCoachTO
	 * @param form The EntityCountByCoachSearchForm
	 * @return The EntityStudentCountByCoachTO wrapper
	 */
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertResponseCountByCoaches(EntityCountByCoachSearchForm form);

	
	/**
	 * Gets a list of {@link Person} objects based on the specified criteria and
	 * {@link org.jasig.ssp.model.reference.EarlyAlertReferral} identifiers.
	 * 
	 * @param earlyAlertStudentSearchTO
	 *            list of {@link EarlyAlertStudentSearchTO} early alert student search transfer object
	 *          
	 * @param earlyAlertStudentSearchTO
	 *            set of standard search criteria see peopleFromCriteria
	 * @param sAndP
	 * 			  Sorting and paging options
	 * @return A list of {@link EarlyAlertStudentReportTO} objects based on the specified criteria
	 *         and special service groups.
	 * @throws ObjectNotFoundException
	 *             If any of the special service groups could not be found.
	 */
	List<EarlyAlertStudentReportTO> getPeopleByEarlyAlertReferralIds(
			EarlyAlertStudentSearchTO earlyAlertStudentSearchTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException;

	/**
	 * Get count of all early alerts which have one or more responses.  No selection criteria other than earlyAlertId
	 * @param earlyAlertIds the list of EA UUIDs
	 * @return the Response counts
	 */
	EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlerts(List<UUID> earlyAlertIds);
	
	Long getEarlyAlertCountByOutcomeCriteria(EarlyAlertStudentSearchTO searchForm)  throws ObjectNotFoundException;
	
	EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlertsByOutcome(List<UUID> earlyAlertIds, UUID outcomeId);
	
	List<EarlyAlertStudentResponseOutcomeReportTO> getEarlyAlertResponseOutcomeTypeForStudentsByCriteria(String outcomeType, EarlyAlertStudentSearchTO searchForm, SortingAndPaging sAndP, SortingAndPaging reportPersonSAndP);

	Long getEarlyAlertOutcomeTypeCountByCriteria(String outcomeType, UUID outcomeId, EarlyAlertStudentSearchTO searchForm) throws ObjectNotFoundException;

}
