package org.jasig.ssp.web.api.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
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
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.service.reference.impl.AbstractReferenceService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.util.DateTerm;

public class SearchParameters {
	
	private static final String COACH_NAME = "coachName";
	private static final String TERM_CODE = "termCode";
	private static final String TERM_NAME = "termName";
	private static final String TERM = "term";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String CAMPUS_NAME = "campusName";
	private static final String SEPCIAL_SERVICE_GROUP_NAMES = "specialServiceGroupNames";
	private static final String STUDENT_TYPE_NAMES = "studentTypeNames";
	private static final String EARLY_ALERT_OUTCOME_NAMES = "earlyAlertOutcomeNames";
	private static final String PROGRAM_STATUS_NAME = "programStatusName";
	private static final String REPORT_DATE = "reportDate";
	private static final String HOME_DEPARTMENT_NAME = "homeDepartmentName";
	private static final String ALL = "All";
	private static final String REPORT_TITLE = "ReportTitle";
	private static final String DATA_FILE = "DataFile";
	private static final String TERM_CODES = "termCodes";
	private static final String TERM_NAMES = "termNames";
	private static final String COHORT_TERM = "cohortTerm";
	private static final String REFERRAL_SOURCE_NAMES = "referralSourceNames";
	private static final String EARLY_ALERT_REFERRAL_NAME = "earlyAlertReferralName";
	private static final String EARLY_ALERT_REFERRAL_NAMES = "earlyAlertReferralNames";
	
	private static final String DEPARTMENT_PLACEHOLDER = "Not Available Yet";

	static Campus getCampus(UUID campusId, CampusService campusService)
			throws ObjectNotFoundException {
		Campus campus = null;
		if (campusId != null) {
			campus = campusService.get(campusId);
		}
		return campus;
	}

	static List<Person> getCoaches(final UUID coachId, PersonService personService)
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
		}

		return coaches;
	}
	
	static List<Term> getTerms(final List<String> termCodes, final TermService termService) 
			throws ObjectNotFoundException{
		List<Term> terms = new ArrayList<Term>();
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
		parameters.put(TERM_CODES, concatNamesFromStrings(termCodes, ALL) );
		parameters.put(TERM_NAMES, concatNamesFromStrings(termNames, ALL));
	}

	static void addCampusToParameters(String label, Campus campus,
			Map<String, Object> parameters) {
		if (campus != null)
			parameters.put(label, campus.getName());
		else
			parameters.put(label, "All");
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
		addUUIDSToMap(SEPCIAL_SERVICE_GROUP_NAMES, ALL, specialServiceGroupIds, parameters,
				(ReferenceService)ssgService);
	}

	@SuppressWarnings("rawtypes")
	static final void addStudentTypesToMap(final List<UUID> studentTypeIds,
			final Map<String, Object> parameters,
			StudentTypeService studentTypeService)
			throws ObjectNotFoundException {
		addUUIDSToMap(STUDENT_TYPE_NAMES, ALL, studentTypeIds, parameters,
				(ReferenceService)studentTypeService);
	}
	
	static final void addHomeDepartment(final String homeDepartment, final Map<String, Object> parameters){
		if(homeDepartment == null || homeDepartment.length() == 0)
			parameters.put(HOME_DEPARTMENT_NAME, DEPARTMENT_PLACEHOLDER);
		else
			parameters.put(HOME_DEPARTMENT_NAME, homeDepartment);
	}

	@SuppressWarnings("rawtypes")
	static final void addReferralSourcesToMap(final List<UUID> referralSourcesIds,
			final Map<String, Object> parameters,
			ReferralSourceService referralSourcesService)
			throws ObjectNotFoundException {
		addUUIDSToMap(REFERRAL_SOURCE_NAMES, ALL, referralSourcesIds, parameters,
				(ReferenceService) referralSourcesService);
	}

	@SuppressWarnings("rawtypes")
	static final void addProgramStatusToMap(final UUID programStatus,
			final Map<String, Object> parameters,
			final ProgramStatusService programStatusService)
			throws ObjectNotFoundException {
		addUUIDToMap(PROGRAM_STATUS_NAME, ALL, programStatus, parameters,
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
		addUUIDSToMap(EARLY_ALERT_OUTCOME_NAMES, ALL, outcomeIds, parameters,
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
		parameters.put(REPORT_DATE, new Date());
	}

	final static void addReportTitleToMap(final String reportTitle,
			final Map<String, Object> parameters) {
		parameters.put(REPORT_TITLE, reportTitle);
	}

	final static void addDataFIleToMap(final String dataFile,
			final Map<String, Object> parameters) {
		parameters.put(DATA_FILE, dataFile);
	}
	
	final static void addCohortTerm(String anticipatedStartTerm, Integer anticipatedStartYear, Map<String, Object> parameters){
		if(anticipatedStartTerm != null && anticipatedStartTerm.length() > 0)
			parameters.put(COHORT_TERM, StringUtils.defaultString(anticipatedStartTerm) + " " + (anticipatedStartYear == null? "" : "and " + anticipatedStartYear.toString()));
		else
			parameters.put(COHORT_TERM, (anticipatedStartYear == null? ALL : anticipatedStartYear.toString()));
	}
	
	@SuppressWarnings("rawtypes")
	final static void addEarlyAlertReferralToMap(UUID earlyAlertReferralId, Map<String, Object> parameters, EarlyAlertReferralService earlyAlertReferralsService) throws ObjectNotFoundException{
		SearchParameters.addUUIDToMap(EARLY_ALERT_REFERRAL_NAME, ALL, earlyAlertReferralId, parameters, (AbstractReferenceService)earlyAlertReferralsService);
	}
	
	@SuppressWarnings("rawtypes")
	static final void addEarlyAlertReferralsToMap(final List<UUID> earlyAlertReferralIds,
			final Map<String, Object> parameters,
			final EarlyAlertReferralService referenceService)
			throws ObjectNotFoundException {
		addUUIDSToMap(EARLY_ALERT_REFERRAL_NAMES, ALL, earlyAlertReferralIds, parameters,
				(ReferenceService) referenceService);
	}

}
