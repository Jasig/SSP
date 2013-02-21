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
package org.jasig.ssp.web.api.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.service.reference.DisabilityTypeService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

public class SearchParameters {
	
	static final String NOT_USED = "Not Used";
	static final String ALL = "ALL";
	private static final String COACH_NAME = "coachName";
	private static final String ODS_COACH_NAME = "odsCoachName";
	private static final String TERM_CODE = "termCode";
	private static final String TERM_NAME = "termName";
	private static final String ROSTER_STATUS = "rosterStatus";
	private static final String TERM = "term";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String CAMPUS_NAME = "campusName";
	private static final String SEPCIAL_SERVICE_GROUP_NAMES = "specialServiceGroupNames";
	private static final String STUDENT_TYPE_NAMES = "studentTypeNames";
	private static final String EARLY_ALERT_OUTCOME_NAMES = "earlyAlertOutcomeNames";
	private static final String PROGRAM_STATUS_NAME = "programStatusName";
	private static final String REPORT_DATE = "reportDate";
	private static final String HOME_DEPARTMENT_NAME = "homeDepartment";
    
	private static final String REPORT_TITLE = "ReportTitle";
	private static final String DATA_FILE = "DataFile";
	private static final String TERM_CODES = "termCodes";
	private static final String TERM_NAMES = "termNames";
	private static final String ANTICIPATED_START_TERM = "anticipatedStartTerm";
	private static final String ACTUAL_START_TERM = "actualStartTerm";
	private static final String REFERRAL_SOURCE_NAMES = "referralSourceNames";
	private static final String EARLY_ALERT_REFERRAL_NAME = "earlyAlertReferralName";
	private static final String EARLY_ALERT_REFERRAL_NAMES = "earlyAlertReferralNames";
	private static final String DISABILITY_STATUS = "disabilityStatus";
	private static final String DISABILITY_TYPE = "disabilityType";
	private static final String STUDENT_COUNT = "studentCount";
	private static final String REPORT_DATE_FORMAT="MMM-dd-yyyy hh:mmaaa";
	private static final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat(REPORT_DATE_FORMAT);
	
	private static final String DEPARTMENT_PLACEHOLDER = "Not Available Yet";

	static Campus getCampus(UUID campusId, CampusService campusService)
			throws ObjectNotFoundException {
		Campus campus = null;
		if (campusId != null) {
			campus = campusService.get(campusId);
		}
		return campus;
	}

	static List<Person> getCoaches(final UUID coachId, String homeDepartment, PersonService personService)
			throws ObjectNotFoundException {
		List<Person> coaches;
		if (coachId != null) {
			Person coach = personService.get(coachId);
			coaches = new ArrayList<Person>();
			coaches.add(coach);
		} else {
			coaches = new ArrayList<Person>(
					personService
							.getAllCurrentCoaches(Person.PERSON_NAME_AND_ID_COMPARATOR));
			
			if(homeDepartment != null && homeDepartment.length() > 0){
				List<Person> homeCoaches = new ArrayList<Person>();
				for(Person coach:coaches){
					if(coach.getStaffDetails() != null && 
							coach.getStaffDetails().getDepartmentName() != null &&
							coach.getStaffDetails().getDepartmentName().equals(homeDepartment))
						homeCoaches.add(coach);
				}
				coaches = homeCoaches;
			}
		}

		return coaches;
	}
	
	static List<Term> getTerms(final List<String> termCodes, final TermService termService) 
			throws ObjectNotFoundException{
		List<Term> terms = new ArrayList<Term>();
		if(termCodes == null)
			return terms;
		for(String termCode:termCodes){
			Term term = termService.getByCode(termCode);
			terms.add(term);
		}
		return terms;
	}
	
	static void addTermsToMap(final List<Term>terms, final Map<String, Object> parameters) 
			throws ObjectNotFoundException{
		final List<String> termCodes = new ArrayList<String>();
		final List<String> termNames = new ArrayList<String>();
		
		for(Term term:terms){
			termCodes.add(term.getCode());
			termNames.add(term.getName());
		}
		parameters.put(TERM_CODES, concatNamesFromStrings(termCodes, NOT_USED) );
		parameters.put(TERM_NAMES, concatNamesFromStrings(termNames, NOT_USED));
	}

	static void addCampusToParameters(String label, Campus campus,
			Map<String, Object> parameters) {
		if (campus != null)
			parameters.put(label, campus.getName());
		else
			parameters.put(label, NOT_USED);
	}

	static void addCampusToParameters(Campus campus,
			Map<String, Object> parameters) {
		addCampusToParameters(CAMPUS_NAME, campus, parameters);
	}

	public List<UUID> cleanSpecialServiceGroupIds(
			List<UUID> specialServiceGroupIds) {
		return cleanUUIDListOfNulls(specialServiceGroupIds);
	}

	@SuppressWarnings({"rawtypes" })
	final static public void addSpecialGroupsNamesToMap(
			final List<UUID> specialServiceGroupIds, final Map<String, Object> parameters,
			SpecialServiceGroupService ssgService)
			throws ObjectNotFoundException {
		addUUIDSToMap(SEPCIAL_SERVICE_GROUP_NAMES, NOT_USED, specialServiceGroupIds, parameters,
				(ReferenceService)ssgService);
	}

	@SuppressWarnings("rawtypes")
	static final void addStudentTypesToMap(final List<UUID> studentTypeIds,
			final Map<String, Object> parameters,
			StudentTypeService studentTypeService)
			throws ObjectNotFoundException {
		addUUIDSToMap(STUDENT_TYPE_NAMES, NOT_USED, studentTypeIds, parameters,
				(ReferenceService)studentTypeService);
	}
	
	static final void addHomeDepartmentToMap(final String homeDepartment, final Map<String, Object> parameters){
		if(homeDepartment == null || homeDepartment.length() <= 0)
			parameters.put(HOME_DEPARTMENT_NAME, NOT_USED);
		else
			parameters.put(HOME_DEPARTMENT_NAME, homeDepartment);
	}
	
	static final void addRosterStatusToMap(final String rosterStatus, final Map<String, Object> parameters){
		if(rosterStatus == null || rosterStatus.length() == 0)
			parameters.put(ROSTER_STATUS, NOT_USED);
		else
			parameters.put(ROSTER_STATUS, rosterStatus);
	}

	@SuppressWarnings("rawtypes")
	static final void addReferralSourcesToMap(final List<UUID> referralSourcesIds,
			final Map<String, Object> parameters,
			ReferralSourceService referralSourcesService)
			throws ObjectNotFoundException {
		addUUIDSToMap(REFERRAL_SOURCE_NAMES, NOT_USED, referralSourcesIds, parameters,
				(ReferenceService) referralSourcesService);
	}

	@SuppressWarnings("rawtypes")
	static final void addProgramStatusToMap(final UUID programStatus,
			final Map<String, Object> parameters,
			final ProgramStatusService programStatusService)
			throws ObjectNotFoundException {
		addUUIDToMap(PROGRAM_STATUS_NAME, NOT_USED, programStatus, parameters,
				(ReferenceService) programStatusService);
	}

	static final Map<String, Object> addDateTermToMap(final DateTerm dateTerm,
			final Map<String, Object> parameters) {
		parameters.put(START_DATE, dateTerm.startDateString());
		parameters.put(END_DATE, dateTerm.endDateString());
		parameters.put(TERM, dateTerm.getTermName());
		parameters.put(TERM_NAME, dateTerm.getTermName());
		parameters.put(TERM_CODE, dateTerm.getTermCode());
		return parameters;
	}

	@SuppressWarnings("rawtypes")
	static final void addEarlyAlertOutcomesToMap(final List<UUID> outcomeIds,
			final Map<String, Object> parameters,
			final EarlyAlertOutcomeService referenceService)
			throws ObjectNotFoundException {
		addUUIDSToMap(EARLY_ALERT_OUTCOME_NAMES, NOT_USED, outcomeIds, parameters,
				(ReferenceService) referenceService);
	}

	static final List<UUID> cleanUUIDListOfNulls(final List<UUID> uuids) {
		ArrayList<UUID> cleanUUIDs = null;
		if (uuids != null) {
			
			Iterator<UUID> iterator = uuids.iterator();
			while (iterator.hasNext()) {
				UUID uuid = iterator.next();
				if (uuid == null)
					continue;
				if(cleanUUIDs == null)
					cleanUUIDs = new ArrayList<UUID>();
				cleanUUIDs.add(uuid);
			}
		}
		return cleanUUIDs;
	}

	static final List<String> cleanStringListOfNulls(final List<String> strs) {
		ArrayList<String> cleanStrs = null;
		if (strs != null) {
			Iterator<String> iterator = strs.iterator();
			while (iterator.hasNext()) {
				String str = iterator.next();
				if (str == null)
					continue;
				if(cleanStrs == null)
					cleanStrs = new ArrayList<String>();
				cleanStrs.add(str);
			}
		}
		return cleanStrs;
	}

	static final void addCoachNameToMap(final PersonTO coach, final Map<String, Object> parameters) {
		addPersonToMap(COACH_NAME, ALL, coach, parameters);
	}

	static final void addPersonToMap(final String label, final String ifValueIsNull,
			PersonTO person, Map<String, Object> parameters) {
		parameters.put(label, getFullName(person, ifValueIsNull));
	}

	private static final String getFullName(final PersonTO person,
			final String ifValueIsNull) {
		return person == null ? ifValueIsNull : person.getFirstName() + " "
				+ person.getLastName();
	}

	static final PersonTO getPerson(final UUID personId, PersonService personService,
			PersonTOFactory personTOFactory) throws ObjectNotFoundException {
		if (personId != null) {
			return personTOFactory.from(personService.get(personId));
		}
		return null;
	}

	private final static String concatNamesFromStrings(final List<String> strs,
			final String valueIfNull) throws ObjectNotFoundException {
		final StringBuffer namesStringBuffer = new StringBuffer();
		if (strs != null && strs.size() > 0) {
			for (String str : strs) {
				namesStringBuffer.append("\u2022 " + str);
				namesStringBuffer.append("    ");
			}
			return namesStringBuffer.toString();
		}
		return valueIfNull;
	}

	final static void addUUIDSToMap(
			final String label,
			final String valueIfNull,
			final List<UUID> ids,
			final Map<String, Object> parameters,
			@SuppressWarnings("rawtypes") ReferenceService referenceService)
			throws ObjectNotFoundException {

		ArrayList<String> strs = new ArrayList<String>();
		if (ids != null && ids.size() > 0) {
			for (UUID id : ids) {
				strs.add(((AbstractReference) referenceService.get(id))
						.getName());
			}

		}
		parameters.put(label, concatNamesFromStrings(strs, valueIfNull));
	}

	final static void addUUIDToMap(
			final String label,
			final String valueIfNull,
			final UUID id,
			final Map<String, Object> parameters,
			@SuppressWarnings("rawtypes") ReferenceService referenceService)
			throws ObjectNotFoundException {

		if (id != null) {
			parameters.put(label,
					((AbstractReference) referenceService.get(id)).getName());
		} else {
			parameters.put(label, valueIfNull);
		}
	}

	final static void addReportDateToMap(final Map<String, Object> parameters) {	
		parameters.put(REPORT_DATE, REPORT_DATE_FORMATTER.format(new Date()));
	}

	final static void addReportTitleToMap(final String reportTitle,
			final Map<String, Object> parameters) {
		parameters.put(REPORT_TITLE, reportTitle);
	}

	final static void addDataFIleToMap(final String dataFile,
			final Map<String, Object> parameters) {
		parameters.put(DATA_FILE, dataFile);
	}
	
	final static void addTerm(String label, String valueIsNull, String term, Integer year, Map<String, Object> parameters){
		if(term != null && term.length() > 0)
			parameters.put(label, StringUtils.defaultString(term) + " " + (year == null? "" : "and " + year.toString()));
		else
			parameters.put(label, (year == null? valueIsNull : year.toString()));
	}
	
	final static void addAnticipatedStartTerm(String anticipatedStartTerm, Integer anticipatedStartYear, Map<String, Object> parameters){
		addTerm(ANTICIPATED_START_TERM, NOT_USED, anticipatedStartTerm, anticipatedStartYear, parameters);
	}
	
	final static void addactualStartTerm(String actualStartTerm, Integer actualStartYear, Map<String, Object> parameters){
		addTerm(ACTUAL_START_TERM, NOT_USED, actualStartTerm, actualStartYear, parameters);	
	}
	
	@SuppressWarnings("rawtypes")
	final static void addEarlyAlertReferralToMap(UUID earlyAlertReferralId, Map<String, Object> parameters, EarlyAlertReferralService earlyAlertReferralsService) throws ObjectNotFoundException{
		addUUIDToMap(EARLY_ALERT_REFERRAL_NAME, NOT_USED, earlyAlertReferralId, parameters, (ReferenceService)earlyAlertReferralsService);
	}
	
	@SuppressWarnings("rawtypes")
	final static void addDisablityStatusToMap(UUID disabilityStatusId, Map<String, Object> parameters, DisabilityStatusService disabilityStatusService) throws ObjectNotFoundException{
		addUUIDToMap(DISABILITY_STATUS, NOT_USED, disabilityStatusId, parameters, (ReferenceService)disabilityStatusService);
	}
	
	@SuppressWarnings("rawtypes")
	final static void addDisabilityTypeToMap(UUID disabilityTypeId, Map<String, Object> parameters, DisabilityTypeService disabilityTypeService) throws ObjectNotFoundException{
		addUUIDToMap(DISABILITY_TYPE, NOT_USED, disabilityTypeId, parameters, (ReferenceService)disabilityTypeService);
	}
	
	@SuppressWarnings("rawtypes")
	static final void addEarlyAlertReferralsToMap(final List<UUID> earlyAlertReferralIds,
			final Map<String, Object> parameters,
			final EarlyAlertReferralService referenceService)
			throws ObjectNotFoundException {
		addUUIDSToMap(EARLY_ALERT_REFERRAL_NAMES, NOT_USED, earlyAlertReferralIds, parameters,
				(ReferenceService) referenceService);
	}
	
	static final void addCoach(UUID coachId, final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm,
			PersonService personService, 
			PersonTOFactory personTOFactory) throws ObjectNotFoundException{
		
		PersonTO coach = getPerson(coachId, personService,
				personTOFactory);
		SearchParameters.addPersonToMap(COACH_NAME, NOT_USED, coach, parameters);
		personSearchForm.setCoach(coach);
	}
	
	static final void addOdsCoach(UUID odsCoachId, final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm, 
			PersonService personService, 
			PersonTOFactory personTOFactory) throws ObjectNotFoundException{
		PersonTO odsCoach = getPerson(odsCoachId, personService,
				personTOFactory);
		SearchParameters.addPersonToMap(ODS_COACH_NAME, NOT_USED, odsCoach, parameters);
		personSearchForm.setOdsCoach(odsCoach);
	}
	
	static final void addDateRange(final Date createDateFrom,
			final Date createDateTo,
			final String termCode,
			final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm,
			final TermService termService
			
			) throws ObjectNotFoundException{
		DateTerm dateTerm = new DateTerm(createDateFrom, createDateTo, termCode, termService);
		addDateTermToMap(dateTerm, parameters);
		
		personSearchForm.setCreateDateFrom(dateTerm.getStartDate());
		personSearchForm.setCreateDateTo(dateTerm.getEndDate());
	}
	
	static final void addReferenceLists(final List<UUID> studentTypeIds,
			final List<UUID> specialServiceGroupIds,
			final List<UUID> referralSourcesIds,
			final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm,
			final StudentTypeService studentTypeService,
			final SpecialServiceGroupService ssgService,
			final ReferralSourceService referralSourcesService
			) throws ObjectNotFoundException{
		
		List<UUID> cleanSpecialServiceGroupIds = cleanUUIDListOfNulls(specialServiceGroupIds);
		List<UUID> cleanStudentTypeIds = cleanUUIDListOfNulls(studentTypeIds);
		List<UUID> cleanReferralSourcesIds = cleanUUIDListOfNulls(referralSourcesIds);
		
		addStudentTypesToMap(cleanStudentTypeIds, parameters, studentTypeService);
		addSpecialGroupsNamesToMap(cleanSpecialServiceGroupIds, parameters, ssgService);
		addReferralSourcesToMap(cleanReferralSourcesIds, parameters, referralSourcesService);	
		
		personSearchForm.setSpecialServiceGroupIds(cleanSpecialServiceGroupIds);
		personSearchForm.setStudentTypeIds(cleanStudentTypeIds);
		personSearchForm.setReferralSourcesIds(cleanReferralSourcesIds);
	}
	
	static final void addReferenceTypes(final UUID programStatus, 
			final UUID disabilityStatusId,
			final Boolean disabilityIsNotNull,
			final String rosterStatus,
			final String homeDepartment,
			final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm, 
			ProgramStatusService programStatusService, 
			DisabilityStatusService disabilityStatusService) throws ObjectNotFoundException {
		
		addProgramStatusToMap(programStatus, parameters, programStatusService);
		addDisablityStatusToMap(disabilityStatusId, parameters, disabilityStatusService);
		addRosterStatusToMap(rosterStatus, parameters);
		addHomeDepartmentToMap(homeDepartment, parameters);
		personSearchForm.setProgramStatus(programStatus);
		personSearchForm.setDisabilityStatusId(disabilityStatusId);
		personSearchForm.setDisabilityIsNotNull(disabilityIsNotNull);
		personSearchForm.setRosterStatus(rosterStatus);
		personSearchForm.setHomeDepartment(homeDepartment);
	}
	
	static final void addReferenceTypes(final UUID programStatus, 
			final UUID disabilityStatusId,
			final UUID disabilityTypeId,
			final Boolean disabilityIsNotNull,
			final String rosterStatus,
			final String homeDepartment,
			final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm, 
			ProgramStatusService programStatusService, 
			DisabilityStatusService disabilityStatusService,
			DisabilityTypeService disabilityTypeService) throws ObjectNotFoundException {
		
		SearchParameters.addReferenceTypes(programStatus, 
				disabilityStatusId, 
				disabilityIsNotNull,
				rosterStatus,
				homeDepartment,
				parameters, 
				personSearchForm, 
				programStatusService, 
				disabilityStatusService);
		
		addDisabilityTypeToMap(disabilityTypeId, parameters, disabilityTypeService);
		personSearchForm.setDisabilityTypeId(disabilityTypeId);
	}
	
	static final void addAnticipatedAndActualStartTerms(String anticipatedStartTerm, 
			Integer anticipatedStartYear, 
			String actualStartTerm, 
			Integer actualStartYear,
			final Map<String, Object> parameters,
			final PersonSearchFormTO personSearchForm){
		
		
		addAnticipatedStartTerm(anticipatedStartTerm, anticipatedStartYear, parameters);
		addactualStartTerm(actualStartTerm, actualStartYear, parameters);
		
		personSearchForm.setAnticipatedStartTerm(anticipatedStartTerm);
		personSearchForm.setAnticipatedStartYear(anticipatedStartYear);
		personSearchForm.setActualStartTerm(actualStartTerm);
		personSearchForm.setActualStartYear(actualStartYear);
	}
	
	static final void addStudentCount(List peopleTO, Map<String, Object> parameters){
		parameters.put(STUDENT_COUNT, peopleTO == null ? 0 : peopleTO.size());
	}
	
	static final SortingAndPaging getReportPersonSortingAndPagingAll(ObjectStatus status){
		List<Pair<String, SortDirection>> sortFields = Lists.newArrayList();
		sortFields.add(new Pair<String, SortDirection>("lastName", SortDirection.ASC));
		sortFields.add(new Pair<String, SortDirection>("firstName", SortDirection.ASC));
		sortFields.add(new Pair<String, SortDirection>("middleName", SortDirection.ASC));
		return new SortingAndPaging(status, sortFields, null, SortDirection.ASC);
	}
}
