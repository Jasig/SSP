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
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
import org.jasig.ssp.transferobject.EmailStudentRequestTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.security.core.GrantedAuthority;

import javax.mail.SendFailedException;
import javax.portlet.PortletRequest;

/**
 * Person service
 */
public interface PersonService extends AuditableCrudService<Person> {

	@Override
	PagingWrapper<Person> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified Person.
	 * 
	 * @param id
	 *            Required identifier for the Person to retrieve. Can not be
	 *            null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	@Override
	Person get(UUID id) throws ObjectNotFoundException;

	Person personFromUsername(String username) throws ObjectNotFoundException;

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 */
	@Override
	Person create(Person obj);

	/**
	 * Updates values of direct Person properties, but not any associated
	 * children or collections.
	 * 
	 * WARNING: Copies system-only (based on business logic rules) properties,
	 * so ensure that the incoming values have already been sanitized.
	 * 
	 * @param obj
	 *            Model instance from which to copy the simple properties.
	 * @see IntakeService
	 */
	@Override
	Person save(Person obj) throws ObjectNotFoundException;

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#INACTIVE}.
	 */
	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	/**
	 * Return a person object for every personId where available.
	 * 
	 * @param personIds
	 * @param sAndP
	 * @return A person object for every personId where available
	 */
	List<Person> peopleFromListOfIds(List<UUID> personIds,
			SortingAndPaging sAndP);

	/**
	 * Retrieves the specified Person by their Student ID (school_id).
	 * 
	 * @param studentId
	 *            Required school identifier for the Person to retrieve. Can not
	 *            be null.
	 *            Also searches the External Database for the identifier,
	 *            creating a Person if an ExternalPerson record exists..
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	Person getBySchoolId(String studentId,boolean commitPerson) throws ObjectNotFoundException;

	/**
	 * Gets a list of {@link Person} objects based on specified criteria
	 * 
	 * @param addressLabelSearchTO
	 *            criteria
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of person objects based on specified criteria
	 * @throws ObjectNotFoundException
	 */
	List<Person> peopleFromCriteria(PersonSearchFormTO addressLabelSearchTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException;

	/**
	 * Gets a list of {@link Person} objects based on the specified criteria and
	 * {@link SpecialServiceGroup} identifiers.
	 * 
	 * @param specialServiceGroupIDs
	 *            list of {@link SpecialServiceGroup} service group identifiers
	 * @param createForSingleSort
	 * @return A list of {@link Person} objects based on the specified criteria
	 *         and special service groups.
	 * @throws ObjectNotFoundException
	 *             If any of the special service groups could not be found.
	 */
	List<Person> peopleFromSpecialServiceGroups(
			List<UUID> specialServiceGroupIDs,
			SortingAndPaging createForSingleSort)
			throws ObjectNotFoundException;

	/**
	 * Get a list of all Coaches
	 * 
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of all coaches
	 */
	PagingWrapper<Person> getAllCoaches(SortingAndPaging sAndP);

	/**
	 * Get a list of all Coaches where you don't need the complete Person
	 * graphs.
	 *
	 * <em>Does not have the same external-to-internal person record
	 * copying side-effects as {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}.
	 * This method only returns coaches that have already been sync'd into
	 * local person records by some other mechanism.</em>
	 *
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of all coaches
	 */
	PagingWrapper<CoachPersonLiteTO> getAllCoachesLite(SortingAndPaging sAndP);
	PagingWrapper<CoachPersonLiteTO> getAllCoachesLite(final SortingAndPaging sAndP, String HomeDepartment);

	/**
	 * Gets all coaches assigned to local Person records regardless of current
	 * permissions. This is as opposed to
	 * {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)} which
	 * is intended to just return "official" coaches, i.e. users known to
	 * act as coaches, regardless of whether they have any assignments at all.
	 *
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of all <em>assigned</em> coaches
	 */
	PagingWrapper<Person> getAllAssignedCoaches(SortingAndPaging sAndP);

	/**
	 * Lighter-weight version of
	 * {@link #getAllAssignedCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}.
	 *
	 * @param sAndP
	 * @return
	 */
	PagingWrapper<CoachPersonLiteTO> getAllAssignedCoachesLite(SortingAndPaging sAndP);
	PagingWrapper<CoachPersonLiteTO> getAllAssignedCoachesLite(SortingAndPaging sAndP, String homeDepartment);

	/**
	 * Gets a collection of <em>all</em> coaches, i.e. the union of
	 * {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)} and
	 * {@link #getAllAssignedCoaches(org.jasig.ssp.util.sort.SortingAndPaging)},
	 * without duplicates.
	 *
	 * <p><em>Be very careful when using this method. Implementation is
	 * likely to use {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}
	 * under the covers, which
     * <a href="https://issues.jasig.org/browse/SSP-470">SSP-470</a> deprecates
     * for performance reasons.</em></p>
	 *
	 * <p>Since we know the implementation would face difficulties implementing
	 * a paged version of this method and we know that all current clients
	 * of this method aren't actually interested in a paged view, we choose to
	 * return a vanilla <code>SortedSet</code> rather than a
	 * <code>PagingWrapper</code></p>
	 *
	 * @param personNameComparator null OK
	 * @return
	 */
	SortedSet<Person> getAllCurrentCoaches(Comparator<Person> personNameComparator);

	/**
	 * (Much) lighter-weight version of
	 * {@link #getAllCurrentCoaches(java.util.Comparator)}.
	 *
	 * <em>Note that unlike {@link #getAllCurrentCoaches(java.util.Comparator)},
	 * this implementation should not be expected to materialize any local
	 * coach records. I.e. this method plays
	 * {@link #getAllCoachesLite(org.jasig.ssp.util.sort.SortingAndPaging)}}
	 * to {@link #getAllCurrentCoaches(java.util.Comparator)}'s
	 * {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}</em>
	 *
	 * @param sortBy
	 * @return
	 */
	SortedSet<CoachPersonLiteTO> getAllCurrentCoachesLite(
			Comparator<CoachPersonLiteTO> sortBy, String homeDepartment);
	
	SortedSet<CoachPersonLiteTO> getAllCurrentCoachesLite(
			Comparator<CoachPersonLiteTO> sortBy);

	Person load(UUID id);

	Person createUserAccount(String username,
			Collection<GrantedAuthority> authorities);

	void setPersonAttributesService(
			final PersonAttributesService personAttributesService);

	/**
	 * Attempt to create a new user account using JSR-168/286
	 * portlet user attributes from the given request.
	 *
	 *
	 * @param  username the login under which to create the account. Potentially
	 *                  null or empty, in which case the implementation can
	 *                  attempt to determine the username on its own, probably
	 *                  from user attributes on <code>portletRequest</code>, but
	 *                  under current usage the caller typically expects a
	 *                  certain username for the new account, so it is exposed
	 *                  as a param here.
	 * @param portletRequest current portlet request (note that associated
	 *                       user attributes map is potentially null or empty)
	 * @return the created Person, never null
	 * @throws UnableToCreateAccountException typically caused by the presence
	 *   of an existing account with conflicting keys or missing required
	 *   user attributes on <code>portletRequest</code>
	 */
	Person createUserAccountForCurrentPortletUser(String username,
			PortletRequest portletRequest)
			throws UnableToCreateAccountException;
	
	PagingWrapper<DisabilityServicesReportTO> getDisabilityReport(PersonSearchFormTO form,
			final SortingAndPaging sAndP) throws ObjectNotFoundException;


	public PagingWrapper<BaseStudentReportTO> getStudentReportTOsFromCriteria(
			final PersonSearchFormTO personSearchFormTO,
			final SortingAndPaging sAndP) throws ObjectNotFoundException;

	PagingWrapper<Person> syncCoaches();

	String getSchoolIdForPersonId(UUID personId) throws ObjectNotFoundException;

	void evict(Person model);

	boolean emailStudent(EmailStudentRequestTO emailRequest) throws ObjectNotFoundException, ValidationException;

	UUID getCoachIdForStudent(PersonTO obj);

	void sendCoachingAssignmentChangeEmail(Person model, UUID oldCoachId) throws ObjectNotFoundException, SendFailedException, ValidationException;

}