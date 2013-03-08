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
import java.util.Map;
import java.util.UUID;
import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * EarlyAlert service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertService
		extends PersonAssocAuditableService<EarlyAlert> {

	/**
	 * Create a new EarlyAlert, assign to the appropriate coordinator, e-mail
	 * all notifications and alerts, and save to persistent storage.
	 * 
	 * @param obj
	 *            EarlyAlert data
	 * @return The updated data object instance.
	 */
	@Override
	EarlyAlert create(EarlyAlert obj) throws ObjectNotFoundException,
			ValidationException;

	/**
	 * Mark an EarlyAlert closed by the current user. No-op if the alert
	 * is already closed.
	 *
	 * @param earlyAlertId
	 * @throws ObjectNotFoundException if the referenced EarlyAlert does
	 *   not exist
	 * @throws ValidationException if a business rule is violated
	 */
	void closeEarlyAlert(UUID earlyAlertId) throws ObjectNotFoundException, ValidationException;

	/**
	 * Count how many open early alerts exist for the specified people
	 * (students).
	 * 
	 * <p>
	 * An 'active' means it has not been closed (has a null closedDate) and has
	 * an ObjectStatus of {@link ObjectStatus#ACTIVE}.
	 * <p>
	 * If list is empty, no results will be returned.
	 * 
	 * @param peopleIds
	 *            personIds for all the students for which to count early
	 *            alerts; required
	 * @return Map of students (personId) with the count of open early alerts
	 *         for each.
	 */
	Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			final Collection<UUID> peopleIds);

	/**
	 * Same as {@link #getCountOfActiveAlertsForPeopleIds(java.util.Collection)}
	 * but for closed alerts.
	 *
	 * @param peopleIds
	 * @return
	 */
	Map<UUID, Number> getCountOfClosedAlertsForPeopleIds(
			final Collection<UUID> peopleIds);

	/**
	 * Similar to {@link org.jasig.ssp.service.external.RegistrationStatusByTermService#applyRegistrationStatusForCurrentTerm(org.jasig.ssp.model.Person)},
	 * but for early alerts.
	 */
	void applyEarlyAlertCounts(Person person);

	/**
	 * Send e-mail ({@link Message}) to the student.
	 * 
	 * <p>
	 * This method is not called during the {@link #create(EarlyAlert)} action.
	 * It must be called by the calling code.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 *             If reference data could not be found.
	 * @throws SendFailedException
	 *             If message send action failed.
	 * @throws ValidationException
	 *             If any data was invalid.
	 */
	void sendMessageToStudent(@NotNull final EarlyAlert earlyAlert)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException;

	/**
	 * Fills early alert parameters for messages.
	 * 
	 * <p>
	 * Also used by early alert response since messages have similar template
	 * parameters.
	 * 
	 * @param earlyAlert
	 * @return Map of template parameters
	 */
	Map<String, Object> fillTemplateParameters(final EarlyAlert earlyAlert);

	Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);
	
	Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);

	Long getCountOfEarlyAlertsForSchoolIds(Collection<String> schoolIds, Campus campus);
	
	Long getCountOfEarlyAlertsByCreatedDate(Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus);

	Long getCountOfEarlyAlertsClosedByDate(Date closedDateFrom, Date closedDateTo, Campus campus, String rosterStatus);
	
	Long getCountOfEarlyAlertStudentsByDate(Date createdDateFrom, Date createdDateTo, Campus campu, String rosterStatuss);

	Long getEarlyAlertCountSetForCritera(EarlyAlertStudentSearchTO searchForm);

	PagingWrapper<EarlyAlertStudentReportTO> getStudentsEarlyAlertCountSetForCritera(EarlyAlertStudentSearchTO searchForm, SortingAndPaging createForSingleSort);
	
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertCountByCoaches(List<Person> coaches, Date createDateFrom, 
			Date createDateTo, List<UUID> studentTypeIds, List<UUID> serviceReasonIds, SortingAndPaging sAndP);

}