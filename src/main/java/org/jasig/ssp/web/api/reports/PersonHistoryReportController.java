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
package org.jasig.ssp.web.api.reports; // NOPMD

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.*;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidAwardTermService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidFileService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.transferobject.*;
import org.jasig.ssp.transferobject.external.ExternalPersonPlanStatusTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.jasig.ssp.transferobject.reports.StudentHistoryTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */
@Controller
@RequestMapping("/1/person")
public class PersonHistoryReportController extends ReportBaseController {

	private static final String REPORT_URL = "/reports/studentHistoryMaster.jasper";
	private static final String REPORT_FILE_TITLE = "StudentHistoryReport-";
	private static final String STUDENT_TO = "studentTO";
    private static final String STUDENT_RECORD_TO = "studentRecordTO";
    private static final String STUDENT_PLAN_TO = "studentPlanTO";
    private static final String STUDENT_MAP_STATUS_TO = "studentMapStatusTO";
    private static final String STUDENT_MAP_PROJECTED_GRADUATION_TERM = "planProjectedGraduationTerm";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonHistoryReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient JournalEntryService journalEntryService;
	@Autowired
	private transient JournalEntryTOFactory journalEntryTOFactory;
	@Autowired
	private transient EarlyAlertService earlyAlertService;
	@Autowired
	private transient EarlyAlertTOFactory earlyAlertTOFactory;
	@Autowired
	private transient TaskService taskService;
	@Autowired
	private transient TaskTOFactory taskTOFactory;
    @Autowired
    private transient ExternalStudentTranscriptService externalStudentTranscriptService;
    @Autowired
    private transient ExternalStudentAcademicProgramService externalStudentAcademicProgramService;
    @Autowired
    private transient ExternalStudentFinancialAidService externalStudentFinancialAidService;
	@Autowired
	private transient ExternalStudentFinancialAidAwardTermService externalStudentFinancialAidAwardTermService;
	@Autowired
	private transient ExternalStudentFinancialAidFileService externalStudentFinancialAidFileService;
    @Autowired
    private transient ExternalPersonPlanStatusService planStatusService;
    @Autowired
    private transient PlanService planService;
	@Autowired
	protected transient SecurityService securityService;

	@RequestMapping(value = "/{personId}/history/print", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	void getAddressLabels(
			final HttpServletResponse response,
			final @PathVariable UUID personId,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {

		final Person person = personService.get(personId);
		final PersonTO personTO = personTOFactory.from(person);
		final SspUser requestor = securityService.currentUser();
        final String schoolId = person.getSchoolId();

		LOGGER.debug("Requester id: " + requestor.getPerson().getId());
		// get all the journal entries for this person
		final PagingWrapper<JournalEntry> journalEntrys = journalEntryService
				.getAllForPerson(person, requestor, null);
		final List<JournalEntryTO> journalEntryTOs = journalEntryTOFactory
				.asTOList(journalEntrys.getRows());
		LOGGER.debug("JournalEntryTOs.size(): " + journalEntryTOs.size());

		// get all the early alerts for this person
		final PagingWrapper<EarlyAlert> earlyAlert = earlyAlertService
				.getAllForPerson(person, null); 
		final Set<EarlyAlertTO> earlyAlertTOs = earlyAlertTOFactory.asTOSet(earlyAlert.getRows());
		LOGGER.debug("EarlyAlertTOs.size(): " + earlyAlertTOs.size());

		// get all the tasks for this person
		final Map<String, List<Task>> taskMap = taskService
				.getAllGroupedByTaskGroup(
						person, requestor, null);
		final Map<String, List<TaskTO>> taskTOMap = new HashMap<String, List<TaskTO>>();
		LOGGER.debug("taskTOMap.size(): " + taskMap.size());

		// change all tasks to TaskTOs
		for (final Map.Entry<String, List<Task>> entry : taskMap.entrySet()) {
			final String groupName = entry.getKey();
			final List<Task> tasks = entry.getValue();
			taskTOMap.put(groupName, taskTOFactory.asTOList(tasks));
		}

        // get financial aid, academic, and transcript info for student summary
        final ExternalStudentRecordsLite record = new ExternalStudentRecordsLite();
        record.setPrograms(externalStudentAcademicProgramService.getAcademicProgramsBySchoolId(schoolId));
        record.setGPA(externalStudentTranscriptService.getRecordsBySchoolId(schoolId));
        record.setFinancialAid(externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId));
		record.setFinancialAidAcceptedTerms(externalStudentFinancialAidAwardTermService.getStudentFinancialAidAwardsBySchoolId(schoolId));
		record.setFinancialAidFiles(externalStudentFinancialAidFileService.getStudentFinancialAidFilesBySchoolId(schoolId));

        final ExternalStudentRecordsLiteTO recordTO = new ExternalStudentRecordsLiteTO(record, null); //null because don't need balance owed

        //get current plan for student summary add projected graduation date as an additional parameter
        Plan checkPlan = planService.getCurrentForStudent(personId);
        PlanTO plan = new PlanTO();
        String  planGraduateTerm = "";

        if ( checkPlan != null ) {
            plan.from(checkPlan);
            if ( plan.getPersonId().equals(personId) ) {
                plan = planService.validate(plan);
            }
            planGraduateTerm = plan.getPlanCourses().get(0).getTermCode();
        }
        final PlanTO planTO = plan;
        final String planProjectedGraduationTerm = planGraduateTerm;

        //get current plan status for student summary
        final ExternalPersonPlanStatusTO mapStatusTO = new ExternalPersonPlanStatusTO();
        ExternalPersonPlanStatus planStatus = planStatusService.getBySchoolId(schoolId);
        if ( planStatus != null ) {
            mapStatusTO.from(planStatus);
        }

        // separate the Students into bands by date
		final List<StudentHistoryTO> studentHistoryTOs = sort(earlyAlertTOs,
				taskTOMap, journalEntryTOs);

        final Map<String, Object> parameters = Maps.newHashMap();
		
		SearchParameters.addReportDateToMap(parameters);
		parameters.put(STUDENT_TO, personTO);
        parameters.put(STUDENT_RECORD_TO, recordTO);
        parameters.put(STUDENT_PLAN_TO, planTO);
        parameters.put(STUDENT_MAP_STATUS_TO, mapStatusTO);
        parameters.put(STUDENT_MAP_PROJECTED_GRADUATION_TERM, planProjectedGraduationTerm);
		
		this.generateReport(response, parameters, studentHistoryTOs, REPORT_URL, reportType, 
				REPORT_FILE_TITLE + personTO.getLastName());

	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	private static final SimpleDateFormat getDateFormatter() {
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
	}

	public static List<StudentHistoryTO> sort(
			final Set<EarlyAlertTO> earlyAlerts,
			final Map<String, List<TaskTO>> taskMap,
			final List<JournalEntryTO> journalEntries) {

		final Map<String, StudentHistoryTO> studentHistoryMap = Maps
				.newHashMap();

		// first, iterate over each EarlyAlertTO, looking for matching dates int
		// eh PersonHistoryTO
		final Iterator<EarlyAlertTO> alertIter = earlyAlerts.iterator();
		while (alertIter.hasNext()) {
			final EarlyAlertTO thisEarlyAlertTO = alertIter.next();
			final String snewDate = getDateFormatter().format(thisEarlyAlertTO
					.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				final StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);

			} else {
				final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addEarlyAlertTO(thisEarlyAlertTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		final Iterator<JournalEntryTO> journalEntryIter = journalEntries
				.iterator();
		while (journalEntryIter.hasNext()) {
			final JournalEntryTO thisJournalEntryTO = journalEntryIter.next();
			final String snewDate = getDateFormatter().format(
					thisJournalEntryTO
							.getCreatedDate());
			if (studentHistoryMap.containsKey(snewDate)) {
				final StudentHistoryTO studentHistoryTO = studentHistoryMap
						.get(snewDate);
				studentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
			} else {
				final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
						snewDate);
				thisStudentHistoryTO.addJournalEntryTO(thisJournalEntryTO);
				studentHistoryMap.put(snewDate, thisStudentHistoryTO);
			}
		}

		// Per the API, the tasks are already broken down into a map, sorted by
		// group.
		// we want to maintain this grouping, but sort these guys based date
		for (final Map.Entry<String, List<TaskTO>> entry : taskMap.entrySet()) {
			final String groupName = entry.getKey();
			final List<TaskTO> tasks = entry.getValue();

			final Iterator<TaskTO> taskIter = tasks.iterator();
			while (taskIter.hasNext()) {
				final TaskTO thisTask = taskIter.next();
				final String snewDate = getDateFormatter().format(thisTask
						.getCreatedDate());
				if (studentHistoryMap.containsKey(snewDate)) {
					final StudentHistoryTO studentHistoryTO = studentHistoryMap
							.get(snewDate);
					studentHistoryTO.addTask(groupName, thisTask);
				} else {
					final StudentHistoryTO thisStudentHistoryTO = new StudentHistoryTO(
							snewDate);
					thisStudentHistoryTO.addTask(groupName, thisTask);
					studentHistoryMap.put(snewDate, thisStudentHistoryTO);
				}
			}
		}

		// at this point, we should have a StudentHistoryTO map with Dates
		final Collection<StudentHistoryTO> studentHistoryTOs = studentHistoryMap
				.values();

		final List<StudentHistoryTO> retVal = new ArrayList<StudentHistoryTO>();
		final Iterator<StudentHistoryTO> studentHistoryTOIter = studentHistoryTOs
				.iterator();
		while (studentHistoryTOIter.hasNext()) {
			final StudentHistoryTO currentStudentHistoryTO = studentHistoryTOIter
					.next();
			currentStudentHistoryTO.createTaskList();
			retVal.add(currentStudentHistoryTO);
		}

		return retVal;
	}
}