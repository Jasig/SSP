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

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.EarlyAlertSearchResultTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.form.EarlyAlertSearchForm;
import org.jasig.ssp.transferobject.reports.EarlyAlertCourseCountsTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertReasonCountsTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.collections.Triple;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	 * @param earlyAlertId the EA UUID
	 * @throws ObjectNotFoundException if the referenced EarlyAlert does
	 *   not exist
	 * @throws ValidationException if a business rule is violated
	 */
	void closeEarlyAlert(UUID earlyAlertId) throws ObjectNotFoundException, ValidationException;
	
	/**
	 * Mark an EarlyAlert open by the current user. No-op if the alert
	 * is already open.
	 *
	 * @param earlyAlertId the EA UUID
	 * @throws ObjectNotFoundException if the referenced EarlyAlert does
	 *   not exist
	 * @throws ValidationException if a business rule is violated
	 */
	void openEarlyAlert(UUID earlyAlertId) throws ObjectNotFoundException, ValidationException;

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
	 * @param peopleIds the collection of people UUIDs
	 * @return count of closed EAs for the passed people UUIDs
	 */
	Map<UUID, Number> getCountOfClosedAlertsForPeopleIds(
			final Collection<UUID> peopleIds);

	/**
	 * Similar to {@link org.jasig.ssp.service.external.RegistrationStatusByTermService#applyRegistrationStatusForCurrentTerm(org.jasig.ssp.model.Person)},
	 * but for early alerts.
	 *
	 * @param person the person object
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
	 * @param earlyAlert the EA
	 * @return Map of template parameters
	 */
	Map<String, Object> fillTemplateParameters(final EarlyAlert earlyAlert);

	Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);
	
	Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);

	Long getCountOfEarlyAlertsForSchoolIds(Collection<String> schoolIds, Campus campus);
	
	Long getEarlyAlertCountForCreatedDateRange(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus);

	Long getClosedEarlyAlertCountForClosedDateRange(Date closedDateFrom, Date closedDateTo, Campus campus, String rosterStatus);

	Long getClosedEarlyAlertsCountForEarlyAlertCreatedDateRange(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus);

	Long getStudentCountForEarlyAlertCreatedDateRange(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, String rosterStatus);

    List<EarlyAlertCourseCountsTO> getStudentEarlyAlertCountSetPerCourses(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, ObjectStatus objectStatus);

	Long getStudentEarlyAlertCountSetPerCoursesTotalStudents(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, ObjectStatus objectStatus);

    List<Triple<String, Long, Long>> getEarlyAlertReasonTypeCountByCriteria(Campus campus, String termCode, Date createdDateFrom, Date createdDateTo, ObjectStatus status);

    List<EarlyAlertReasonCountsTO> getStudentEarlyAlertReasonCountByCriteria(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, ObjectStatus objectStatus);

	Long getStudentEarlyAlertReasonCountByCriteriaTotalStudents(String termCode, Date createdDateFrom, Date createdDateTo, Campus campus, ObjectStatus objectStatus);

	Long getEarlyAlertCountSetForCriteria(EarlyAlertStudentSearchTO searchForm);

	PagingWrapper<EarlyAlertStudentReportTO> getStudentsEarlyAlertCountSetForCriteria(EarlyAlertStudentSearchTO searchForm, SortingAndPaging createForSingleSort);
	
	PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertCountByCoaches(EntityCountByCoachSearchForm form);

	Map<UUID,Number> getResponsesDueCountEarlyAlerts(List<UUID> personIds);
	
	void sendAllEarlyAlertReminderNotifications();
	
	PagedResponse<EarlyAlertSearchResultTO> searchEarlyAlert(EarlyAlertSearchForm form);

}