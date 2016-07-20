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

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.security.core.GrantedAuthority;
import javax.portlet.PortletRequest;
import java.util.*;


/**
 * Person service
 */
public interface PersonService extends AuditableCrudService<Person> {

	/**
	 * Gets all Persons based on sorting and paging
	 * @param sAndP SortingAndPaging
     * @return
     */
	@Override
	PagingWrapper<Person> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified Person by uuid.
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

    /**
     * Returns internal-only person by Username (Doesn't sync from external)
     * @param username
     * @return
     * @throws ObjectNotFoundException
     */
	Person personFromUsername(String username) throws ObjectNotFoundException;

    /**
     * Returns internal-only person by SchoolId (Doesn't sync from external!)
     * @param schoolId
     * @return
     * @throws ObjectNotFoundException
     */
    Person personFromSchoolId(String schoolId) throws ObjectNotFoundException;

    /**
     * Syncs SpecialServiceGroups for specified Person. Helper method to sync SSGs after
     *  saving a Person but where getInternalOrExternalPersonBySchoolId was run with commit == false.
     * @param studentToSync
     */
    void syncSpecialServiceGroups(Person studentToSync);

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
	List<Person> peopleFromListOfIds(List<UUID> personIds, SortingAndPaging sAndP);

    /**
     * Attempts to retrieve a specified Person by their Student ID (school_id), if not then pulls from external.
     *
     * @param studentId
     *            Required school identifier for the Person to retrieve. Should not be null/empty.
     * @return The specified Person instance.
     * @throws ObjectNotFoundException If the supplied identifier does not exist in the database at all
     */
	Person getInternalOrExternalPersonBySchoolIdLite(String studentId) throws ObjectNotFoundException;

    /**
     * Attempts to retrieve a specified Person by their Student ID (school_id), if not then pulls from external.
     *
     * *NOTE*: If school_id is *not* in Person (internal), it then retrieves from external_person
     *    if exists and syncs the record.
     *
     * *WARNING*: Syncs person from external_person and syncs special_service_groups
     *  so it may be slow. Best use is when you need a completely up-to-date person
     *    such as in add/edit scenarios. Use the "light" method getInternalOrExternalPersonBySchoolIdLite instead.
     *
     * @param studentId
     *            Required school identifier for the Person to retrieve. Can not be null/empty.
     *            Also searches the External Database for the identifier, creating a Person if an ExternalPerson
     *            record exists.
     * @param commitPerson If true, saves the syncd person in the database.
     * @return The specified Person instance.
     * @throws ObjectNotFoundException If the supplied identifier does not exist in the database at all
     */
	Person getInternalOrExternalPersonBySchoolId(String studentId,boolean commitPerson) throws ObjectNotFoundException;

    /**
     * Retrieves the specified Person by their User name (userName) and syncs from external for the most up to date
     *  record as possible.
     *
     * *WARNING*: Syncs person from external_person and syncs special_service_groups
     *  so it may be slow. Best use is when you need a completely up-to-date person
     *    such as in add/edit scenarios.
     *
     * @param username
     *            Required school identifier for the Person to retrieve. Can not
     *            be null.
     *            Also searches the External Database for the identifier,
     *            creating a Person if an ExternalPerson record exists..
     * @exception ObjectNotFoundException
     *                If the supplied identifier does not exist in the database.
     * @return The specified Person instance.
     */
    Person getSyncedByUsername(final String username, final Boolean commit) throws ObjectNotFoundException;

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
	List<Person> peopleFromCriteria(PersonSearchFormTO addressLabelSearchTO, final SortingAndPaging sAndP)
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

    /**
     * Get a list of all Coaches where you don't need the complete Person graphs by sAndP and homeDepartment.
     *
     * <em>Does not have the same external-to-internal person record
     * copying side-effects as {@link #getAllCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}.
     * This method only returns coaches that have already been sync'd into
     * local person records by some other mechanism.</em>
     *
     * @param sAndP
     * @param HomeDepartment
     * @return
     */
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
	 * Get a list of all assigned Coaches where you don't need the complete Person graphs by sAndP and homeDepartment.
     *
	 * @param sAndP
	 * @return
	 */
	PagingWrapper<CoachPersonLiteTO> getAllAssignedCoachesLite(SortingAndPaging sAndP);

    /**
     * Lighter-weight version of
     * {@link #getAllAssignedCoaches(org.jasig.ssp.util.sort.SortingAndPaging)}.
     * Get a list of all assigned Coaches where you don't need the complete Person graphs by sAndP and homeDepartment.
     *
     * @param sAndP
     * @param homeDepartment
     * @return
     */
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
	SortedSet<CoachPersonLiteTO> getAllCurrentCoachesLite(Comparator<CoachPersonLiteTO> sortBy, String homeDepartment);

    /**
     * Gets all current coaches use CoachLite model
     * @param sortBy
     * @return
     */
	SortedSet<CoachPersonLiteTO> getAllCurrentCoachesLite(Comparator<CoachPersonLiteTO> sortBy);

    /**
     * Loads a Person
     * @param id
     * @return
     */
	Person load(UUID id);

    /**
     * Create a user account with specified authorities
     * @param username
     * @param authorities
     * @return
     */
	Person createUserAccount(String username, Collection<GrantedAuthority> authorities);

    /**
     * Sets the PersonAttributes Service
     * @param personAttributesService
     */
	void setPersonAttributesService(final PersonAttributesService personAttributesService);

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
	Person createUserAccountForCurrentPortletUser(String username, PortletRequest portletRequest)
            throws UnableToCreateAccountException;

    /**
     * Get DisabilityReport for person(s) by criteria in the PersonSearchForm
     * @param form
     * @param sAndP
     * @return
     * @throws ObjectNotFoundException
     */
	PagingWrapper<DisabilityServicesReportTO> getDisabilityReport(PersonSearchFormTO form, final SortingAndPaging sAndP)
            throws ObjectNotFoundException;

    /**
     * Get Student Report(s) based on Sorting and Paging and criteria in PersonSearchForm
     * @param personSearchFormTO
     * @param sAndP
     * @return
     * @throws ObjectNotFoundException
     */
	 PagingWrapper<BaseStudentReportTO> getStudentReportTOsFromCriteria(final PersonSearchFormTO personSearchFormTO,
                                                        final SortingAndPaging sAndP) throws ObjectNotFoundException;

    /**
     * Syncs Coaches between internal and external
     * @return
     */
	PagingWrapper<Person> syncCoaches();

    /**
     * Returns a Person's school_id based on their UUID person.id
     * @param personId
     * @return
     * @throws ObjectNotFoundException
     */
	String getSchoolIdForPersonId(UUID personId) throws ObjectNotFoundException;

    /**
     * Removes from session the supplied model
     * @param model
     */
	void evict(Person model);

    /**
     * Get a student's assigned coach uuid that corresponds to the coaches person.id (if set)
     * @param obj
     * @return
     */
	UUID getCoachIdForStudent(PersonTO obj);
}