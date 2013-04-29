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
package org.jasig.ssp.web.api.external;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.impl.JsonSerializerMap;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentAcademicProgramTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentFinancialAidTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentRecordsTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentTestTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentTranscriptTermTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonDemographicsService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTestService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptTermService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.RecentActivityTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.external.ExternalStudentFinancialAidTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTestTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptCourseTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptTermTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.tool.IntakeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private transient ConfigService configService;
	
	@Autowired
	private transient EarlyAlertTOFactory earlyAlertTOFactory;
	
	@Autowired
	private transient JournalEntryTOFactory journalEntryTOFactory;
	
	@Autowired
	private transient TaskTOFactory taskTOFactory;
	
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
	private transient ExternalStudentFinancialAidTOFactory externalStudentFinancialAidTOFactory;
	
	@Autowired
	private transient PersonDemographicsService personDemographicsService;
	
	@Autowired
	private transient SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntakeController.class);
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
		
		ExternalStudentRecordsTO recordTO = new ExternalStudentRecordsTO(record, getBalancedOwed(id, schoolId));
		return recordTO;
	}
	
	@RequestMapping(value = "/transcript/currentcourses", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalStudentTranscriptCourseTO> loadCurrentCourses(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		Term currentTerm = termService.getCurrentTerm();
		List<ExternalStudentTranscriptCourseTO> courses = externalStudentTranscriptCourseFactory.asTOList(
				externalStudentTranscriptCourseService.getTranscriptsBySchoolIdAndTermCode(schoolId, currentTerm.getCode()));
		Map<String,String> mappings = statusCodeMappings();
		
		String defaultStatusCode = getDefaultStatusCode(mappings);
		
		for(ExternalStudentTranscriptCourseTO course:courses){
			Person person = null;
			try{
				person = personService.getBySchoolId(course.getFacultySchoolId());
			}finally
			{
				
			}
			if(person != null)
				course.setFacultyName(person.getFullName());
			
			if(mappings != null){
				if(mappings.containsKey(course.getStatusCode())){
					course.setStatusCode(mappings.get(course.getStatusCode()));
				}else if(defaultStatusCode != null)
					course.setStatusCode(defaultStatusCode);
			}
		}
		return courses;
	}
	
	private BigDecimal getBalancedOwed(UUID id, String schoolId) throws ObjectNotFoundException{
		BigDecimal balanceOwed = null;
		try{
			PersonDemographics demographics = personDemographicsService.get(id);
			balanceOwed = demographics.getBalanceOwed();
		}catch(Exception exception){
			
		}finally{
			
		}
		
		if(balanceOwed == null){
			try{
				ExternalPerson externalPerson = externalStudentService.getBySchoolId(schoolId);
				balanceOwed = externalPerson.getBalanceOwed();
			}catch(Exception exception){
				
			}finally{
				
			}
		}
		return balanceOwed;
	}
	
	private String getDefaultStatusCode(Map<String,String> mappings){
		String v = null;
		if(mappings.containsKey("default"))
			v = mappings.get("default");
		return v;
	}
	private Map<String,String> statusCodeMappings() throws ObjectNotFoundException{
		String codeMappings = configService.getByName("status_code_mappings").getValue();
		ObjectMapper m = new ObjectMapper();
		Map<String,String> statusCodeMap = null;
	    try {
			statusCodeMap = m.readValue(codeMappings, new HashMap<String,String>().getClass());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return statusCodeMap;
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
		UUID coachId = person.getCoach().getId();
		SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, 0, 1000, "createdDate", "DESC", "createdDate");
		PagingWrapper<EarlyAlert> earlyAlerts = earlyAlertService.getAllForPerson(person, sAndP);
		SspUser currentUser = securityService.currentUser();
		List<EarlyAlertTO> earlyAlertTOs = earlyAlertTOFactory.asTOList(earlyAlerts.getRows());
		
		PagingWrapper<JournalEntry> journalEntries = journalEntryService.getAllForPerson(person, currentUser, sAndP);
		
		List<JournalEntryTO> journalEntriesTOs = journalEntryTOFactory.asTOList(journalEntries.getRows());
		
		PagingWrapper<Task> actions = taskService.getAllForPerson(person, currentUser, sAndP);
		
		List<TaskTO> actionsTOs = taskTOFactory.asTOList(actions.getRows());
		
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
	
	private String getPersonLiteName(PersonLiteTO person){
		return person.getFirstName() + " " + person.getLastName();
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
		
		return externalStudentTestTOFactory.asTOList(externalStudentTestService.getStudentTestResults(schoolId));
	}
	
	@RequestMapping(value = "/financialaid/summary", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentFinancialAidTO loadFinancialAidSummary(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		ExternalStudentFinancialAid record = externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId);
		

		final ExternalStudentFinancialAidTO recordTO = externalStudentFinancialAidTOFactory.from(record);
		
		return recordTO;
	}
	
	String getStudentId(UUID id) throws ObjectNotFoundException{
		return personService.get(id).getSchoolId();
	}
	
	

}
