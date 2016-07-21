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
package org.jasig.ssp.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.dao.DirectoryPersonSearchDao;
import org.jasig.ssp.dao.PersonSearchDao;
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.*;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentSpecialServiceGroupService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.reports.CaseLoadSearchTO;
import org.jasig.ssp.util.csvwriter.CaseloadCsvWriterHelper;
import org.jasig.ssp.util.csvwriter.CustomizableCaseloadCsvWriterHelper;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * PersonSearch service implementation
 */
@Service
public class PersonSearchServiceImpl implements PersonSearchService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSearchServiceImpl.class);
	
	@Autowired
	private transient PersonSearchDao dao;
	
	@Autowired
	private transient DirectoryPersonSearchDao directoryPersonDao;
   
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

	@Autowired
	private transient PlanService planService;

    @Autowired
    private transient ExternalStudentSpecialServiceGroupService externalStudentSpecialServiceGroupService;

    @Autowired
    private transient ExternalStudentFinancialAidService externalStudentFinancialAidService;

	@Autowired
	private transient ExternalStudentAcademicProgramService externalStudentAcademicProgramService;

	@Autowired
	private transient ExternalStudentTranscriptService externalStudentTranscriptService;


	@Override
	@Transactional
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
	@Transactional
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
		
		for (final PersonSearchResult2 record : results) {
			if (appts.containsKey(record.getId())) {
				record.setCurrentAppointmentStartTime(appts.get(
						record.getId()));
			}

			if (earlyAlertCounts.containsKey(record.getId())) {
				record.setActiveAlerts(earlyAlertCounts.get(record
						.getId()).intValue());
			}
			
			if (numberEarlyAlertResponsesRequired.containsKey(record.getId())) {
				record.setNumberEarlyAlertResponsesRequired(numberEarlyAlertResponsesRequired.get(record
						.getId()));
			}
		}
		
		List<PersonSearchResult2> filteredResults = null;
		if(form.getEarlyAlertResponseLate() != null){
			filteredResults = new ArrayList<PersonSearchResult2>();
			if(form.getEarlyAlertResponseLate().equals(PersonSearchRequest.EARLY_ALERT_RESPONSE_RESPONSE_OVERDUE)){
				for(PersonSearchResult2 result:results){
					if(numberEarlyAlertResponsesRequired.containsKey(result.getPersonId()) 
							&& numberEarlyAlertResponsesRequired.get(result.getPersonId()).intValue() > 0){
						filteredResults.add(result);
					}
				}
			}else if(form.getEarlyAlertResponseLate().equals(PersonSearchRequest.EARLY_ALERT_RESPONSE_RESPONSE_CURRENT)){
				for(PersonSearchResult2 result:results){
					if((!numberEarlyAlertResponsesRequired.containsKey(result.getPersonId()) 
							|| numberEarlyAlertResponsesRequired.get(result.getPersonId()).intValue() <= 0) && result.getActiveAlerts() > 0){
						filteredResults.add(result);
					}
				}
			}else {
				for(PersonSearchResult2 result:results){
					if(result.getActiveAlerts() > 0){
						filteredResults.add(result);
					}
				}
			}
		}else
			filteredResults = results;

		int size = filteredResults.size();
		List<PersonSearchResult2> sortedAndPaged = new ArrayList<PersonSearchResult2>();
		try {
			sortedAndPaged = (List<PersonSearchResult2>)(List<?>)form.getSortAndPage().sortAndPageList((List<Object>)(List<?>)filteredResults);
		} catch (NoSuchFieldException e) {
			LOGGER.error("Field not Found", e);
		} catch (SecurityException e) {
			LOGGER.error("Field not allowed", e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Class not Found", e);
		}
		return new PagingWrapper<PersonSearchResult2>(size, sortedAndPaged);
	}
	

	@Override
	@Transactional
	public PagingWrapper<PersonSearchResult2> caseLoadFor(
			final ProgramStatus programStatus, 
			@NotNull final Person coach,
			@NotNull final String personTableType,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		
		PersonSearchRequest form = new PersonSearchRequest();
		
		//added to handle multiple coach searches
		if (coach != null) {
            form.setCoach(Lists.newArrayList(coach));
        }

        if (programStatus != null) {
            form.setProgramStatus(Lists.newArrayList(programStatus));
        }
        
		form.setPersonTableType(personTableType);
		form.setSortAndPage(sAndP);
		return searchPersonDirectory(form);
	}

	@Override
	@Transactional
	public Collection<CoachCaseloadRecordCountForProgramStatus>
		currentCaseloadCountsByStatus(CaseLoadSearchTO searchForm) {

		Collection<CoachCaseloadRecordCountForProgramStatus> daoResult =
				daoCaseload.currentCaseLoadCountsByStatus(searchForm, null).getRows();

		// see notes in merge...() on daoResult sorting expectations
		return mergeCaseloadCountsWithOfficialCoaches(daoResult, searchForm.getHomeDepartment());
	}

	@Override
	@Transactional
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
	@Transactional
	public void reassignStudents(CaseloadReassignmentRequestTO obj) throws ObjectNotFoundException {
		Person coach = personService.get(obj.getCoachId());
		daoCaseload.reassignStudents(obj,coach);
	}


	@Override
	@Transactional
	public PagingWrapper<PersonSearchResult2> searchPersonDirectory(
			PersonSearchRequest form) {
		return directoryPersonDao.search(form);
	}

	
	@Override
	@Transactional
	public void refreshDirectoryPerson(){
		directoryPersonDao.refreshDirectoryPerson();
	}


	@Override
	@Transactional
	public void refreshDirectoryPersonBlue(){
		directoryPersonDao.refreshDirectoryPersonBlue();
	}


	@Override
	// explicitly leaving out @Transactional. We really shouldn't need it on most of the
	// methods in this class (or at most readOnly), but it's been there historically
	// on it and most of its peers. This export scenario is a special case, though, where
	// we really don't want to be holding open a transaction if we can help it.
	public void exportableCaseLoadFor(CaseloadCsvWriterHelper csvWriterHelper,
			ProgramStatus programStatus, Person coach,
			SortingAndPaging buildSortAndPage) throws IOException {
			PersonSearchRequest form = new PersonSearchRequest();
			
			ArrayList<Person> coachList = new ArrayList<Person>();	
			coachList.add(coach);
			form.setCoach(coachList);
			
			ArrayList<ProgramStatus> programStatusList = new ArrayList<ProgramStatus>();
			programStatusList.add(programStatus);			
			form.setProgramStatus(programStatusList);
			
			form.setSortAndPage(buildSortAndPage);
			directoryPersonDao.exportableSearch(csvWriterHelper,form);
    }


	@Override
	// explicitly leaving out @Transactional. We really shouldn't need it on most of the
	// methods in this class (or at most readOnly), but it's been there historically
	// on it and most of its peers. This export scenario is a special case, though, where
	// we really don't want to be holding open a transaction if we can help it.
	public void exportDirectoryPersonSearch(
			PrintWriter writer, PersonSearchRequest form) throws IOException {
				
		directoryPersonDao.exportableSearch(new CaseloadCsvWriterHelper(writer),form);

	}

	@Override
	@Transactional
	public Long caseLoadCountFor(ProgramStatus programStatus, Person coach,
			SortingAndPaging buildSortAndPage) {
		PersonSearchRequest form = new PersonSearchRequest();

		ArrayList<Person> coachList = new ArrayList<Person>();
		coachList.add(coach);
		form.setCoach(coachList);
		
		
		ArrayList<ProgramStatus> programStatusList = new ArrayList<ProgramStatus>();
		programStatusList.add(programStatus);
		
		form.setProgramStatus(programStatusList);
		form.setSortAndPage(buildSortAndPage);
		
		return directoryPersonDao.getCaseloadCountFor( form , buildSortAndPage);
	}


	@Override
	@Transactional
	public Long searchPersonDirectoryCount(PersonSearchRequest form) {
		return directoryPersonDao.getCaseloadCountFor( form , form.getSortAndPage());
	}


    @Override
    // explicitly leaving out @Transactional. See comment on exportDirectoryPersonSearch
    public void exportDirectoryPersonSearchCustomizable(
            PrintWriter writer, PersonSearchRequest form, Map<Integer, Boolean> customOptions) throws IOException {

        final CustomizableCaseloadCsvWriterHelper csvWriterHelper =
                new CustomizableCaseloadCsvWriterHelper(writer, customOptions);

        if (csvWriterHelper != null) {
			final Map<String, PersonSearchResultFull> resultsBySchoolIdMap = Maps.newHashMap();
			final List<PersonSearchResultFull> personDirectoryResults = directoryPersonDao.exportableCustomizableSearch(form);

            if (!CollectionUtils.isEmpty(personDirectoryResults)) {
                final List<UUID> personUUIDs = Lists.newArrayList();
				final List<String> personSchoolIds = Lists.newArrayList();
                final List <String> externalOnlySchoolIds = Lists.newArrayList();

				loadIdListsAndResultMap(personUUIDs, personSchoolIds, externalOnlySchoolIds,
                        resultsBySchoolIdMap, personDirectoryResults);

                loadCustomizableDataForOptions(customOptions, personUUIDs, personSchoolIds, externalOnlySchoolIds,
                        resultsBySchoolIdMap);
            }
            csvWriterHelper.write(resultsBySchoolIdMap.values(), -1L);
        }
    }

    private void loadIdListsAndResultMap(List<UUID> resultUUIDs, List<String> resultSchoolIds,
                                         List<String> externalOnlySchoolIds,
										 Map<String, PersonSearchResultFull> resultMap,
										 		List<PersonSearchResultFull> results) {
        for (PersonSearchResultFull person : results) {
            if (person.getId() == null) {
                externalOnlySchoolIds.add(person.getSchoolId());
            } else {
                resultUUIDs.add(person.getId());
            }

			resultSchoolIds.add(person.getSchoolId());
            resultMap.put(person.getSchoolId(), person);
        }
    }

    private void loadCustomizableDataForOptions (Map<Integer, Boolean> customOptions, List<UUID> personUUIDs,
										 List<String> personSchoolIds, List<String> externalOnlySchoolIds,
                                                 Map<String,PersonSearchResultFull> resultMap) {

		if (customOptions.get(11) || customOptions.get(5)) {
			//sapStatus and financialAidGpa
			final List<ExternalStudentFinancialAid> sapAndFinancialAidGpas = externalStudentFinancialAidService.
					getStudentFinancialAidBySchoolIds(personSchoolIds);

			for (ExternalStudentFinancialAid sapFinancialIterator : sapAndFinancialAidGpas) {
				resultMap.get(sapFinancialIterator.getSchoolId()).setFinancialAidGpaAndSapStatus(
						sapFinancialIterator.getFinancialAidGpa(), sapFinancialIterator.getSapStatus());
			}
		}
		if (customOptions.get(3)) {
			//demographics
		}
		if (customOptions.get(13)) {
			//financialAidCompletionRate
		}
		if (customOptions.get(4)) {
			//academicProgram (code/name/intended program at admit)
			final List<ExternalStudentAcademicProgram> programs = externalStudentAcademicProgramService.
					getBatchedAcademicProgramsBySchoolIds(personSchoolIds);

			for (ExternalStudentAcademicProgram programIterator : programs) {
				resultMap.get(programIterator.getSchoolId()).setAcademicProgramAndIntended(
						programIterator.getProgramCode(), programIterator.getProgramName(), programIterator.getIntendedProgramAtAdmit());
			}
		}
		if (customOptions.get(6)) {
			//academicStanding
			final List<ExternalStudentTranscript> transcripts = externalStudentTranscriptService.
					getBatchedRecordsBySchoolIds(personSchoolIds);

			for (ExternalStudentTranscript transcriptIterator : transcripts) {
				resultMap.get(transcriptIterator.getSchoolId()).setAcademicStanding(transcriptIterator.getAcademicStanding());
			}
		}
		if (customOptions.get(8) || customOptions.get(9) || customOptions.get(10) || customOptions.get(7)) {
            //serviceReasons, referralSources, specialServiceGroups, department

            //external only students
            final Map<String, Set<SpecialServiceGroup>> externalOnlySSGsBySchoolId =
                    externalStudentSpecialServiceGroupService.getMultipleStudentsExternalSSGsSyncedAsInternalSSGs(
                            externalOnlySchoolIds);
            for (String schoolId : externalOnlySSGsBySchoolId.keySet()) {
                resultMap.get(schoolId).setSpecialServiceGroups(externalOnlySSGsBySchoolId.get(schoolId));
            }

            //internal students
			final List<Person> persons = personService.peopleFromListOfIds(personUUIDs, null);
			for (Person personIterator : persons) {
				resultMap.get(personIterator.getSchoolId()).setDepartmentServiceReasonsReferralSourcesAndSpecialServiceGroups(
						personIterator.getNullSafeDepartmentName(), personIterator.getServiceReasons(),
						personIterator.getReferralSources(), personIterator.getSpecialServiceGroups());
			}
		}
		if (customOptions.get(14)) {
			//map data
			final List<Plan> plans = planService.getCurrentPlansForStudents(personUUIDs);

			for (Plan planIterator : plans ) {
				final AuditPerson modifiedBy = planIterator.getModifiedBy();
				resultMap.get(planIterator.getPerson().getSchoolId()).setMapData(planIterator.getName(),
						planIterator.getProgramCode(), planIterator.getCatalogYearCode(),
							planIterator.getOwner().getFullName(), planIterator.getIsFinancialAid(),
								planIterator.getIsF1Visa(), (modifiedBy.getFirstName() + " " + modifiedBy.getLastName()),
									planIterator.getModifiedDate());
			}
		}
	}
}
