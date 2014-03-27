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
package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.dao.PersonSearchDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.CaseLoadSearchTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * PersonSearch service implementation
 */
@Service
@Transactional
public class PersonSearchServiceImpl implements PersonSearchService {

	@Autowired
	private transient PersonSearchDao dao;
   
	@Autowired
	private transient PersonProgramStatusService personProgramStatus;
	
	@Autowired
	private transient AppointmentService appointmentService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;
	
	@Autowired
	private transient CaseloadDao daoCaseload;
	
	@Autowired
	private transient ProgramStatusService programStatusService;
	
	@Autowired
	private transient PersonService personService;

	@Override
	public PagingWrapper<PersonSearchResult> searchBy(
			final ProgramStatus programStatus,
			final Boolean requireProgramStatus,
			final Boolean outsideCaseload,
			final String searchTerm, final Person advisor,
			final SortingAndPaging sAndP)
			throws ObjectNotFoundException, ValidationException {

		final PagingWrapper<Person> people = dao.searchBy(programStatus,
				(requireProgramStatus == null || programStatus != null ? Boolean.TRUE : requireProgramStatus),
				(outsideCaseload == null ? Boolean.FALSE : outsideCaseload),
				searchTerm, advisor, sAndP);

		final Collection<PersonSearchResult> personSearchResults = Lists
				.newArrayList();
		for (final Person person : people) {
			personSearchResults.add(new PersonSearchResult(person, // NOPMD
					personProgramStatus));
		}

		return new PagingWrapper<PersonSearchResult>(people.getResults(),
				personSearchResults);
	}
	

	@Override
	public PagingWrapper<PersonSearchResult2> search2(PersonSearchRequest form) {
		
		List<PersonSearchResult2> results = dao.search(form);
		
		final List<UUID> peopleIds = Lists.newArrayList();
		for (final PersonSearchResult2 record : results) {
			peopleIds.add(record.getId());
		}

		final Map<UUID, Date> appts = appointmentService
				.getCurrentAppointmenDateForPeopleIds(peopleIds);

		final Map<UUID, Number> earlyAlertCounts = earlyAlertService
				.getCountOfActiveAlertsForPeopleIds(peopleIds);
		
		final Map<UUID, Number> numberEarlyAlertResponsesRequired = earlyAlertService.
				getResponsesDueCountEarlyAlerts(peopleIds);
		
		List<PersonSearchResult2> filteredResults = null;
		if(form.getEarlyAlertResponseLate() != null){
			filteredResults = new ArrayList<PersonSearchResult2>();
			if(form.getEarlyAlertResponseLate()){
				for(PersonSearchResult2 result:results){
					if(numberEarlyAlertResponsesRequired.containsKey(result.getPersonId()) 
							&& numberEarlyAlertResponsesRequired.get(result.getPersonId()).intValue() > 0){
						filteredResults.add(result);
					}
				}
			}else{
				for(PersonSearchResult2 result:results){
					if(!numberEarlyAlertResponsesRequired.containsKey(result.getPersonId()) 
							|| numberEarlyAlertResponsesRequired.get(result.getPersonId()).intValue() <= 0){
						filteredResults.add(result);
					}
				}
			}
		}else
			filteredResults = results;

		for (final PersonSearchResult2 record : filteredResults) {
			if (appts.containsKey(record.getId())) {
				record.setCurrentAppointmentStartTime(appts.get(
						record.getId()));
			}

			if (earlyAlertCounts.containsKey(record.getId())) {
				record.setNumberOfEarlyAlerts(earlyAlertCounts.get(record
						.getId()));
			}
			
			if (numberEarlyAlertResponsesRequired.containsKey(record.getId())) {
				record.setNumberEarlyAlertResponsesRequired(numberEarlyAlertResponsesRequired.get(record
						.getId()));
			}
		}

		return new PagingWrapper<PersonSearchResult2>(filteredResults.size(), filteredResults);
	}
	

	@Override
	public PagingWrapper<PersonSearchResult2> caseLoadFor(
			final ProgramStatus programStatus, @NotNull final Person coach,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {

		
		ProgramStatus programStatusOrDefault;

		// programStatus : <programStatusId>, default to Active
		if (programStatus == null) {
			programStatusOrDefault = programStatusService
					.get(ProgramStatus.ACTIVE_ID);
		} else {
			programStatusOrDefault = programStatus;
		}
		
		PersonSearchRequest from = new PersonSearchRequest();
		from.setCoach(coach);
		from.setProgramStatus(programStatusOrDefault);

		
		return search2(from);
	}

	@Override
	public Collection<CoachCaseloadRecordCountForProgramStatus>
		currentCaseloadCountsByStatus(CaseLoadSearchTO searchForm) {

		Collection<CoachCaseloadRecordCountForProgramStatus> daoResult =
				daoCaseload.currentCaseLoadCountsByStatus(searchForm, null).getRows();

		// see notes in merge...() on daoResult sorting expectations
		return mergeCaseloadCountsWithOfficialCoaches(daoResult, searchForm.getHomeDepartment());
	}

	@Override
	public Collection<CoachCaseloadRecordCountForProgramStatus>
		caseLoadCountsByStatus(
			List<UUID> studentTypeIds,
			Date programStatusDateFrom,
			Date programStatusDateTo,
			String homeDepartment) {

		Collection<CoachCaseloadRecordCountForProgramStatus> daoResult =
				daoCaseload.caseLoadCountsByStatus(studentTypeIds,
						programStatusDateFrom, programStatusDateTo, homeDepartment, null).getRows();

		// see notes in merge...() on daoResult sorting expectations
		return mergeCaseloadCountsWithOfficialCoaches(daoResult, null);
	}


	private Collection<CoachCaseloadRecordCountForProgramStatus>
		mergeCaseloadCountsWithOfficialCoaches(
			Collection<CoachCaseloadRecordCountForProgramStatus> daoResult, String homeDepartment) {

		// We assume daoResult ordering matches that in
		// PERSON_NAME_COMPARATOR and we live with that fragility b/c there is
		// currently no way to construct a SortingAndPaging instance that
		// doesn't enforce a page size max. But for this particular use case
		// there's really not much point in enforcing that limit... the entire
		// result set is eventually going to be read into memory before being
		// handed to Jasper Reports. Could just sort again here, but we're
		// already putting the GC to the test with all these intermediate and
		// potentially quite large data structures.
		SortedSet<CoachPersonLiteTO> allCurrentCoaches = getAllCurrentCoachesSortedByName(homeDepartment);
		Set<UUID> coachIdsWithCaseloads = Sets.newHashSet();
		for ( CoachCaseloadRecordCountForProgramStatus countForStatus : daoResult ) {
			coachIdsWithCaseloads.add(countForStatus.getCoachId());
		}
		List<CoachCaseloadRecordCountForProgramStatus> merged =
				Lists.newArrayListWithCapacity(daoResult.size() + allCurrentCoaches.size());

		Iterator<CoachPersonLiteTO> coachIter = allCurrentCoaches.iterator();
		CoachPersonLiteTO mergable = nextPersonFromNotHavingIdIn(coachIter, coachIdsWithCaseloads);
		for ( CoachCaseloadRecordCountForProgramStatus countForStatus : daoResult ) {
			if ( mergable == null ) {
				merged.add(countForStatus);
				continue;
			}
			while ( mergable != null &&
					CoachPersonLiteTO.COACH_PERSON_LITE_TO_NAME_AND_ID_COMPARATOR.compare(mergable, countForStatus) < 0 ) {
				merged.add(asPlaceholderCoachCaseloadRecordCountForProgramStatus(mergable));
				mergable = nextPersonFromNotHavingIdIn(coachIter, coachIdsWithCaseloads);
			}
			merged.add(countForStatus);
		}
		while ( mergable != null ) {
			merged.add(asPlaceholderCoachCaseloadRecordCountForProgramStatus(mergable));
			mergable = nextPersonFromNotHavingIdIn(coachIter, coachIdsWithCaseloads);
		}
		return merged;
	}

	private CoachPersonLiteTO nextPersonFromNotHavingIdIn(Iterator<CoachPersonLiteTO> personIter,
											   Set<UUID> coachIdsWithCaseloads) {
		if ( !(personIter.hasNext() ) ) {
			return null;
		}
		while ( personIter.hasNext() ) {
			CoachPersonLiteTO next = personIter.next();
			if ( !(coachIdsWithCaseloads.contains(next.getId())) ) {
				return next;
			}
		}
		return null;
	}

	private CoachCaseloadRecordCountForProgramStatus
	asPlaceholderCoachCaseloadRecordCountForProgramStatus(CoachPersonLiteTO person) {
		return new CoachCaseloadRecordCountForProgramStatus(
				person.getId(),
				ProgramStatus.ACTIVE_ID,
				0,
				null,
				null,
				person.getFirstName(),
				null,
				person.getLastName(),
				person.getDepartmentName());
	}


	private SortedSet<CoachPersonLiteTO> getAllCurrentCoachesSortedByName(String homeDepartment) {
		return personService.getAllCurrentCoachesLite(CoachPersonLiteTO.COACH_PERSON_LITE_TO_NAME_AND_ID_COMPARATOR, homeDepartment);
	}

	@Override
	public void reassignStudents(CaseloadReassignmentRequestTO obj) throws ObjectNotFoundException {
		Person coach = personService.get(obj.getCoachId());
		daoCaseload.reassignStudents(obj,coach);
	}

}