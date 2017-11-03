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
package org.jasig.ssp.web.api.external;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.factory.external.*;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.external.*;
import org.jasig.ssp.model.reference.EnrollmentStatus;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.*;
import org.jasig.ssp.service.external.*;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EnrollmentStatusService;
import org.jasig.ssp.transferobject.*;
import org.jasig.ssp.transferobject.external.*;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/1/person/{id}")
public class ExternalStudentRecordsController extends AbstractBaseController {

	
	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient TermService termService;
	
	@Autowired
	private transient EarlyAlertService earlyAlertService;
	
	@Autowired
	private transient JournalEntryService journalEntryService;
	
	@Autowired
	private transient TaskService taskService;
	
	@Autowired
	private transient PlanService planService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient EarlyAlertTOFactory earlyAlertTOFactory;
	
	@Autowired
	private transient JournalEntryTOFactory journalEntryTOFactory;
	
	@Autowired
	private transient TaskTOFactory taskTOFactory;
	
	@Autowired
	private transient PlanTOFactory planTOFactory;
	
	@Autowired
	private transient ExternalStudentTranscriptService externalStudentTranscriptService;
	
	@Autowired
	private transient ExternalStudentAcademicProgramService externalStudentAcademicProgramService;
	
	@Autowired
	private transient ExternalStudentAcademicProgramTOFactory programFactory;
		
	@Autowired
	private transient ExternalStudentRecordsTOFactory factory;
		
	@Autowired
	private transient  ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;
	
	@Autowired
	private transient  ExternalPersonService externalStudentService;
	
	@Autowired
	private transient  ExternalStudentTranscriptTermService externalStudentTranscriptTermService;
	
	@Autowired
	private transient  ExternalStudentTranscriptTermTOFactory externalStudentTranscriptTermTOFactory;
	
	@Autowired
	private transient  ExternalStudentTranscriptCourseTOFactory externalStudentTranscriptCourseFactory;
	
	@Autowired
	private transient ExternalStudentTestTOFactory externalStudentTestTOFactory;
	
	@Autowired
	private transient ExternalStudentTestService externalStudentTestService;
	
	@Autowired
	private transient ExternalStudentFinancialAidService externalStudentFinancialAidService;
	
	@Autowired
	private transient ExternalStudentFinancialAidAwardTermService externalStudentFinancialAidAwardTermService;
	
	@Autowired
	private transient ExternalStudentFinancialAidFileService externalStudentFinancialAidFileService;
	
	@Autowired
	private transient ExternalStudentFinancialAidTOFactory externalStudentFinancialAidTOFactory;

	@Autowired
	private transient ExternalStudentRiskIndicatorService externalStudentRiskIndicatorService;

	@Autowired
	private transient ExternalStudentRiskIndicatorTOFactory externalStudentRiskIndicatorTOFactory;

	@Autowired
	private transient ExternalCareerDecisionStatusService externalCareerDecisionStatusService;

	@Autowired
	private transient ExternalCareerDecisionStatusTOFactory externalCareerDecisionStatusTOFactory;
	
	@Autowired
	private transient PersonDemographicsService personDemographicsService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient EnrollmentStatusService enrollmentStatusService;

    @Autowired
    private transient ExternalStudentTranscriptNonCourseEntityService externalStudentTranscriptNonCourseEntityService;


	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalStudentRecordsController.class);


    @Override
	protected Logger getLogger() {
		return LOGGER;
	}

	
	/**
	 * Using the Student UUID passed, return the ExternalStudentRecordsLiteTO in its current state,
	 * creating it if necessary.
	 * 
	 * @param id
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/transcript/summary", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentRecordsLiteTO loadSummaryStudentRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		final ExternalStudentRecordsLite record = new ExternalStudentRecordsLite();

		record.setPrograms(externalStudentAcademicProgramService.getAcademicProgramsBySchoolId(schoolId));
		record.setGPA(externalStudentTranscriptService.getRecordsBySchoolId(schoolId));
		record.setFinancialAid(externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId));		
		record.setFinancialAidAcceptedTerms(externalStudentFinancialAidAwardTermService.getStudentFinancialAidAwardsBySchoolId(schoolId));
		record.setFinancialAidFiles(externalStudentFinancialAidFileService.getStudentFinancialAidFilesBySchoolId(schoolId));
		ExternalStudentRecordsLiteTO recordTO = new ExternalStudentRecordsLiteTO(record, getBalancedOwed(id, schoolId));

		return recordTO;
	}
	
	@RequestMapping(value = "/transcript/full", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentRecordsTO loadFullStudentRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		final ExternalStudentRecords record = new ExternalStudentRecords();
		
		record.setPrograms(externalStudentAcademicProgramService.getAcademicProgramsBySchoolId(schoolId));
		record.setGPA(externalStudentTranscriptService.getRecordsBySchoolId(schoolId));
		record.setFinancialAid(externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId));
		record.setTerms(externalStudentTranscriptCourseService.getTranscriptsBySchoolId(schoolId));		
		record.setFinancialAidAcceptedTerms(externalStudentFinancialAidAwardTermService.getStudentFinancialAidAwardsBySchoolId(schoolId));
		record.setFinancialAidFiles(externalStudentFinancialAidFileService.getStudentFinancialAidFilesBySchoolId(schoolId));
        record.setNonCourseEntities(externalStudentTranscriptNonCourseEntityService.getNonCourseTranscriptsBySchoolId(schoolId));
		ExternalStudentRecordsTO recordTO = new ExternalStudentRecordsTO(record, getBalancedOwed(id, schoolId));

		//update the course faculty names
		updateFactultyNames(recordTO); 
		
		return recordTO;
	}

	
	
	//Faculty names are not added to the external courses by default
	//this method should fix ssp-3041 - Scody
	private void updateFactultyNames(ExternalStudentRecordsTO recordTO) {		
		List<ExternalStudentTranscriptCourseTO> courses = recordTO.getTerms();
		if (courses!=null) {
			for (ExternalStudentTranscriptCourseTO course : courses) {
				try {
					Person person = StringUtils.isBlank(course.getFacultySchoolId()) ? null : personService.getInternalOrExternalPersonBySchoolIdLite(course.getFacultySchoolId());
					if (person != null) {
						course.setFacultyName(person.getFullName());
					}
				} catch (ObjectNotFoundException e) {
					course.setFacultyName("None Listed");
					LOGGER.debug("FACULTY SCHOOL ID WAS NOT RESOLVED WHILE LOADING TRANSCRIPT RECORD.  Faculty School_id: " + course.getFacultySchoolId() + " Student ID: " + course.getSchoolId() + " Course: " + course.getFormattedCourse());
				}
			}
		}
	}
	
	@RequestMapping(value = "/transcript/currentcourses", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalStudentTranscriptCourseTO> loadCurrentCourses(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		Term currentTerm;
		try
		{
			currentTerm = termService.getCurrentTerm();
		}
		catch(ObjectNotFoundException e)
		{
			currentTerm = new Term();
			LOGGER.error("CURRENT TERM NOT SET, org.jasig.ssp.web.api.external.ExternalStudentRecordsController.loadCurrentCourses(UUID) is being called but will not function properly");
		}
		List<ExternalStudentTranscriptCourseTO> courses = externalStudentTranscriptCourseFactory.asTOList(
				externalStudentTranscriptCourseService.getTranscriptsBySchoolIdAndTermCode(schoolId, currentTerm.getCode()));
		Collection<EnrollmentStatus> mappings = statusCodeMappings();
		
		String defaultStatusCode = getDefaultStatusCode(mappings);
		
		for(ExternalStudentTranscriptCourseTO course:courses){
			try{
				Person person = StringUtils.isBlank(course.getFacultySchoolId()) ? null : personService.getInternalOrExternalPersonBySchoolIdLite(course.getFacultySchoolId());
				if (person != null) {
					course.setFacultyName(person.getFullName());
				}
			}catch(ObjectNotFoundException e)
			{
				course.setFacultyName("None Listed");
				LOGGER.debug("FACULTY SCHOOL ID WAS NOT RESOLVED WHILE LOADING TRANSCRIPT RECORD.  Factulty School_id: "+course.getFacultySchoolId()+" Student ID: "+course.getSchoolId()+" Course: "+course.getFormattedCourse());
			}
			
			if(StringUtils.isBlank(course.getStatusCode()))
			{
				course.setStatusCode(defaultStatusCode);
			}
			else
			if(mappings != null && !mappings.isEmpty())
			{
				for (EnrollmentStatus enrollmentStatus : mappings) 
				{
					if(enrollmentStatus.getCode().equals(course.getStatusCode()))
					{   
						course.setStatusCode(enrollmentStatus.getName());
					}
				}
			}
		}
		return courses;
	}
	
	private BigDecimal getBalancedOwed(UUID id, String schoolId) throws ObjectNotFoundException{
		BigDecimal balanceOwed = null;
		try{
			balanceOwed = id == null ? null : personDemographicsService.getBalancedOwed(id);
		}catch(Exception exception){
			getLogger().debug("Failed to load balance owed for internal person id {}", id, exception);
			return null;
		}
		
		if(balanceOwed == null){
			ExternalPerson externalPerson;
			try{
				 externalPerson = schoolId == null ? null : externalStudentService.getBySchoolId(schoolId);
			}catch(ObjectNotFoundException exception){
				getLogger().debug("Failed to load balance owed for external person schoolId {}", schoolId, exception);
				return null;
			}
			balanceOwed = externalPerson == null ? null : externalPerson.getBalanceOwed();
		}
		return balanceOwed;
	}
	
	private String getDefaultStatusCode(Collection<EnrollmentStatus> mappings){
		for (EnrollmentStatus enrollmentStatus : mappings) {
			if("default".equals(enrollmentStatus.getCode()))
			{
				return enrollmentStatus.getName();
			}
		}
		return null;
	}
	
	private Collection<EnrollmentStatus> statusCodeMappings() throws ObjectNotFoundException{
		PagingWrapper<EnrollmentStatus> allStatuses = enrollmentStatusService.getAll(new SortingAndPaging(ObjectStatus.ALL));
		return allStatuses.getRows();
	}
	
	@RequestMapping(value = "/transcript/term", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalStudentTranscriptTermTO> loadTermStudentRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		return externalStudentTranscriptTermTOFactory.asTOList(
				externalStudentTranscriptTermService.getExternalStudentTranscriptTermsBySchoolId(schoolId));
	}
	 
	
	@RequestMapping(value = "/studentactivity", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<RecentActivityTO> loadRecentStudentActivity(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		List<RecentActivityTO> recentActivities = new ArrayList<RecentActivityTO>();
		Person person = personService.get(id);
		SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, 0, 1000, "createdDate", "DESC", "createdDate");

		PagingWrapper<EarlyAlert> earlyAlerts = earlyAlertService.getAllForPerson(person, sAndP);
		SspUser currentUser = securityService.currentUser();
		List<EarlyAlertTO> earlyAlertTOs = earlyAlertTOFactory.asTOList(earlyAlerts.getRows());
		
		PagingWrapper<JournalEntry> journalEntries = journalEntryService.getAllForPerson(person, currentUser, sAndP);
		
		List<JournalEntryTO> journalEntriesTOs = journalEntryTOFactory.asTOList(journalEntries.getRows());
		
		PagingWrapper<Task> actions = taskService.getAllForPerson(person, currentUser, sAndP);
		
		List<TaskTO> actionsTOs = taskTOFactory.asTOList(actions.getRows());
		
		PagingWrapper<Plan> plans = planService.getAllForStudent(
				SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ALL, 0,1000, null, null, null), 
				id);
		
		List<PlanTO> planTOs = planTOFactory.asTOList(plans.getRows());
		
		for(EarlyAlertTO earlyAlert:earlyAlertTOs){
			if(earlyAlert.getClosedDate() != null){
			recentActivities.add(new RecentActivityTO(earlyAlert.getClosedById(), earlyAlert.getClosedByName(), 
					"Early Alert Closed", 
					earlyAlert.getClosedDate()));
			}else{
				recentActivities.add(new RecentActivityTO(earlyAlert.getCreatedBy().getId(),
						getPersonLiteName(earlyAlert.getCreatedBy()), 
						"Early Alert Created", 
						earlyAlert.getCreatedDate()));
			}
		}
		
		for(JournalEntryTO journalEntry:journalEntriesTOs){
				recentActivities.add(new RecentActivityTO(journalEntry.getCreatedBy().getId(),
						getPersonLiteName(
						journalEntry.getCreatedBy()), 
						"Journal Entry", 
						journalEntry.getEntryDate()));
		}
		
		for(TaskTO action:actionsTOs){
			if(action.isCompleted()){
				recentActivities.add(new RecentActivityTO(action.getModifiedBy().getId(),
						getPersonLiteName(action.getModifiedBy()), 
						"Action Plan Task Created", 
						action.getCompletedDate()));
			}else{
			recentActivities.add(new RecentActivityTO(action.getCreatedBy().getId(),
					getPersonLiteName(action.getCreatedBy()), 
					"Action Plan Task Created", 
					action.getCreatedDate()));
			}
		}
		
		for(PlanTO planTO:planTOs){
			Date testDate = DateUtils.addDays(planTO.getCreatedDate(), 1);
			String planName = planTO.getName();
			if(planTO.getModifiedDate().before(testDate)){
				recentActivities.add(new RecentActivityTO(planTO.getCreatedBy().getId(),
						getPersonLiteName(
								planTO.getCreatedBy()), 
						"Map Plan (" + planName + ") Created", 
						planTO.getModifiedDate()));
			}else{
				recentActivities.add(new RecentActivityTO(planTO.getModifiedBy().getId(),
					getPersonLiteName(
							planTO.getModifiedBy()), 
					"Map Plan (" + planName + ") Updated", 
					planTO.getModifiedDate()));
			}
		}
		
		if(person.getStudentIntakeCompleteDate() != null){
			recentActivities.add(new RecentActivityTO(person.getCoach().getId(),
					person.getCoach().getFullName(), 
					"Student Intake Completed", 
					person.getStudentIntakeCompleteDate()));
		}
		if(person.getStudentIntakeRequestDate() != null){
			recentActivities.add(new RecentActivityTO(person.getCoach().getId(),
					person.getCoach().getFullName(), 
					"Student Intake Requested", 
					person.getStudentIntakeRequestDate()));
		}
			
		Collections.sort(recentActivities, RecentActivityTO.RECENT_ACTIVITY_TO_DATE_COMPARATOR);	
		return recentActivities;
	}
	

	@RequestMapping(value = "/financialaid/summary", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentFinancialAidTO loadFinancialAidSummary(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		ExternalStudentFinancialAid record = externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId);

        if (record != null) {
            return externalStudentFinancialAidTOFactory.from(record);
        } else {
            return new ExternalStudentFinancialAidTO();
        }
	}
	

	/**
	 * Using the Student UUID passed, return the ExternalStudentRecordsLiteTO in its current state,
	 * creating it if necessary.
	 * 
	 * @param id
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalStudentTestTO> loadStudentTestRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		List<ExternalStudentTestTO> studentTestTOs =  externalStudentTestTOFactory.asTOList(externalStudentTestService.getStudentTestResults(schoolId, id));
		return studentTestTOs;
	}
	
	/**
	 * Using the Student UUID passed, return the ExternalStudentRecordsLiteTO in its current state,
	 * creating it if necessary.
	 * 
	 * @param id
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException, IOException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/test/details", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public String getTestProviderDetails(final @PathVariable UUID id,
			final @RequestParam(required = true) String testCode,
			final @RequestParam(required = false) String subTestCode,
			HttpServletResponse httpServletResponse)
			throws ObjectNotFoundException, IOException {
		Person person = personService.get(id);
		String url = (String)externalStudentTestService.getTestDetails(testCode, subTestCode, person);
		return "redirect:" +  url;
	}

	@RequestMapping(value = "/risk", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_RISK_READ')")
	public @ResponseBody List<ExternalStudentRiskIndicatorTO> getStudentRiskIndicators(final @PathVariable UUID id)
		throws ObjectNotFoundException {
		final String schoolId = getStudentId(id);
		if ( schoolId == null ) {
			throw new ObjectNotFoundException(id, Person.class.getName());
		}
		final List<ExternalStudentRiskIndicatorTO> externalStudentRiskIndicatorTOs =
				externalStudentRiskIndicatorTOFactory.asTOList(externalStudentRiskIndicatorService.getBySchoolId(schoolId));
		return externalStudentRiskIndicatorTOs;
	}

	@RequestMapping(value = "/careerstatus", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody ExternalCareerDecisionStatusTO getStudentCareerStatus(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		final String schoolId = getStudentId(id);
		if ( schoolId == null ) {
			throw new ObjectNotFoundException(id, Person.class.getName());
		}

        final ExternalCareerDecisionStatus careerDecisionStatus = externalCareerDecisionStatusService.getStudentCareerStatusBySchoolId(schoolId);

        if (careerDecisionStatus != null) {
            return externalCareerDecisionStatusTOFactory.from(careerDecisionStatus);
        } else {
            return new ExternalCareerDecisionStatusTO();
        }
	}
	
	private String getPersonLiteName(PersonLiteTO person){
		return person.getFirstName() + " " + person.getLastName();
	}
	
	String getStudentId(UUID personId) throws ObjectNotFoundException{
		return personService.getSchoolIdForPersonId(personId);
	}

}
