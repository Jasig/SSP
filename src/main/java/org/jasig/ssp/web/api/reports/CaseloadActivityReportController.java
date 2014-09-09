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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.CaseLoadActivityReportTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Service methods for Reporting on Caseload Activity
 * <p>
 * Mapped to URI path <code>/1/report/caseloadactivity</code>
 */
@Controller
@RequestMapping("/1/report/caseloadactivity")
public class CaseloadActivityReportController extends ReportBaseController<CaseLoadActivityReportTO> {

	private static String REPORT_URL = "/reports/caseLoadActivity.jasper";
	private static String REPORT_FILE_TITLE = "CaseLoad_Activity_Report";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadActivityReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	protected transient StudentTypeService studentTypeService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	protected transient ServiceReasonService serviceReasonService;	
	
	@Autowired
	protected transient SpecialServiceGroupService ssgService;
	
	@Autowired
	protected transient TermService termService;
	
	@Autowired
	protected transient JournalEntryService journalEntryService;
	@Autowired
	protected transient TaskService taskService;
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getCaseLoadActivity(
			final HttpServletResponse response,
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

		// populate coaches to search for
		
		final DateTerm dateTerm =  new DateTerm(createDateFrom,  createDateTo, termCode, termService);

		final List<UUID> cleanStudentTypeIds = SearchParameters.cleanUUIDListOfNulls(studentTypeIds);
		final List<UUID> cleanServiceReasonIds = SearchParameters.cleanUUIDListOfNulls(serviceReasonIds);
		final List<UUID> cleanSpecialServiceGroupIds = SearchParameters.cleanUUIDListOfNulls(specialServiceGroupIds);
		List<Person> coaches = SearchParameters.getCoaches(coachId, homeDepartment, personService);
		Collections.sort(coaches, Person.PERSON_NAME_AND_ID_COMPARATOR);
		
		final Map<String, Object> parameters = Maps.newHashMap();
		
		
		SearchParameters.addDateTermToMap(dateTerm, parameters);
		SearchParameters.addStudentTypesToMap(cleanStudentTypeIds, parameters, studentTypeService);
		SearchParameters.addServiceReasonToMap(cleanServiceReasonIds, parameters, serviceReasonService);
		SearchParameters.addSpecialGroupsNamesToMap(cleanSpecialServiceGroupIds, parameters, ssgService);
		
		SearchParameters.addHomeDepartmentToMap(homeDepartment, parameters);
		
		if(coaches.size() == 0){
			renderReport(response, parameters, null, REPORT_URL, reportType, REPORT_FILE_TITLE);
			return;
		}
		
		List<CaseLoadActivityReportTO> caseLoadActivityReportList = new ArrayList<CaseLoadActivityReportTO>();
		
		EntityCountByCoachSearchForm form = new EntityCountByCoachSearchForm(coaches, 
				dateTerm.getStartDate(), 
				dateTerm.getEndDate(), 
				cleanStudentTypeIds,
				cleanServiceReasonIds,
				cleanSpecialServiceGroupIds,
				null);
		
		final PagingWrapper<EntityStudentCountByCoachTO> journalCounts = journalEntryService.getStudentJournalCountForCoaches(form);
		
		final PagingWrapper<EntityStudentCountByCoachTO> taskCounts = taskService.getStudentTaskCountForCoaches(form);
		
		final PagingWrapper<EntityStudentCountByCoachTO> earlyAlertCounts = earlyAlertService.getStudentEarlyAlertCountByCoaches(form);
		
		final PagingWrapper<EntityStudentCountByCoachTO> earlyAlertResponseCounts = earlyAlertResponseService.getStudentEarlyAlertResponseCountByCoaches(form);
		
		
		 Map<UUID, EntityStudentCountByCoachTO> indexedJournals = getIndexedByCoaches(journalCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedTasks = getIndexedByCoaches(taskCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedEarlyAlerts = getIndexedByCoaches(earlyAlertCounts.getRows());
		 Map<UUID, EntityStudentCountByCoachTO> indexedEarlyAlertResponses = getIndexedByCoaches(earlyAlertResponseCounts.getRows());
		 

		for (Person coach:coaches) {	
			UUID sspUserId = coach.getId();
			EntityStudentCountByCoachTO studentsJournal = getEntityStudentCountByCoachTO(indexedJournals,  sspUserId);
			EntityStudentCountByCoachTO studentsTask = getEntityStudentCountByCoachTO(indexedTasks,  sspUserId);
			EntityStudentCountByCoachTO studentsEarlyAlerts = getEntityStudentCountByCoachTO(indexedEarlyAlerts,  sspUserId);
			EntityStudentCountByCoachTO studentsEarlyAlertResponses = getEntityStudentCountByCoachTO(indexedEarlyAlertResponses,  sspUserId);
	
			CaseLoadActivityReportTO caseLoadActivityReportTO = new CaseLoadActivityReportTO(
					coach.getFirstName(), coach.getLastName(),
					getEntityCount(studentsJournal), getStudentCount(studentsJournal),
					getEntityCount(studentsTask), getStudentCount(studentsTask),
					getEntityCount(studentsEarlyAlerts), getStudentCount(studentsEarlyAlerts),
					getEntityCount(studentsEarlyAlertResponses));

			caseLoadActivityReportList.add(caseLoadActivityReportTO);
		}

		
		renderReport(response, parameters, caseLoadActivityReportList, REPORT_URL, reportType, REPORT_FILE_TITLE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	private  Map<UUID, EntityStudentCountByCoachTO>  getIndexedByCoaches(Collection<EntityStudentCountByCoachTO> entities){
			return EntityStudentCountByCoachTO.getIndexedListByCoach(new ArrayList<EntityStudentCountByCoachTO>(entities));
	}
	
	private EntityStudentCountByCoachTO getEntityStudentCountByCoachTO(Map<UUID, EntityStudentCountByCoachTO> entities, UUID coachId){
		if(coachId != null && entities.containsKey(coachId))
			return entities.get(coachId);
		return null;
	}
	
	private Long getStudentCount(EntityStudentCountByCoachTO countObject){
		return countObject == null ?  0L:countObject.getStudentCount();
	}
	
	private Long getEntityCount(EntityStudentCountByCoachTO countObject){
		return countObject == null ?  0L:countObject.getEntityCount();
	}

}