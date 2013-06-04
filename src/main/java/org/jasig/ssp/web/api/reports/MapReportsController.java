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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.transferobject.reports.EarlyAlertTermCaseCountsTO;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusByCourseTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.DateTerm;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/1/report/map")
public class MapReportsController extends ReportBaseController {
	private static String REPORT_URL_NUMBER_COURSES_IN_PLAN = "/reports/numberCoursesInPlan.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_COURSES_IN_PLAN = "Number_Of_Courses_In_Plan";
	
	private static String REPORT_URL_NUMBER_PLANS_BY_ADVISOR = "/reports/numberPlansByAdvisor.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_PLANS_BY_ADVISOR = "Number_Of_Plans_By_Advisor";
	
	private static String REPORT_URL_NUMBER_STUDENTS_BY_STATUS = "/reports/numberStudentsByStatus.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_STUDENTS_BY_STATUS = "Number_Of_Students_By_Status";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadActivityReportController.class);
	
	@Autowired
	protected transient TermService termService;
	
	@Autowired
	protected transient PlanService planService;
	
	
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	
	@RequestMapping(value = "/numbercourses", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getNumberOfPlansByCourse(
			final HttpServletResponse response,		
			final @RequestParam(required = false) String courseNumber,
			final @RequestParam(required = false) String subjectAbbreviation,
			final @RequestParam(required = false) String formattedCourse,
			final @RequestParam(required = false) String termCode,			
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
	
		List<Term> terms = null;
		if(!StringUtils.isEmpty(termCode))
			terms = SearchParameters.getTerms(Lists.newArrayList(termCode), termService);
		
		SearchPlanTO form = new SearchPlanTO(null, subjectAbbreviation, courseNumber, formattedCourse, terms, null, null);
		 List<PlanCourseCountTO> counts = planService.getPlanCourseCount(form);
		 
		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addTermsToMap( terms, parameters);
		SearchParameters.addPlanSearchForm(form, parameters);

		
		generateReport( response,  parameters, counts,  REPORT_URL_NUMBER_COURSES_IN_PLAN, 
				 reportType, REPORT_FILE_TITLE_NUMBER_COURSES_IN_PLAN);
	}
	
	@RequestMapping(value = "/numberplansbyadvisor", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getNumberOfPlansByAdvisor(
			final HttpServletResponse response,		
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,			
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		DateTerm dateTerm = new DateTerm(createDateFrom, createDateTo, termCode, termService);
		List<Term> terms = new ArrayList<Term>();
		if(dateTerm != null && dateTerm.getTerm() != null)
			terms.add(dateTerm.getTerm());
		SearchPlanTO form = new SearchPlanTO(null, null, null, null, terms, dateTerm.getStartDate(), dateTerm.getEndDate());
		List<PlanAdvisorCountTO> counts = planService.getAdvisorsPlanCount(form);
		
		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addPlanSearchForm(form, parameters);
		SearchParameters.addDateTermToMap(dateTerm, parameters);
		
		generateReport( response,  parameters, counts,  REPORT_URL_NUMBER_PLANS_BY_ADVISOR, 
				 reportType, REPORT_FILE_TITLE_NUMBER_PLANS_BY_ADVISOR);
	}
	
	@RequestMapping(value = "/numberstudentsbystatus", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getNumberOfStudentsByStatus(
			final HttpServletResponse response,		
			final @RequestParam(required = false) String courseNumber,
			final @RequestParam(required = false) String subjectAbbreviation,
			final @RequestParam(required = false) String formattedCourse,
			final @RequestParam(required = false) String planStatus,
			final @RequestParam(required = false) String termCode,			
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		List<Term> terms = null;
		if(!StringUtils.isEmpty(termCode))
			terms = SearchParameters.getTerms(Lists.newArrayList(termCode), termService);
		
		SearchPlanTO form = new SearchPlanTO(planStatus, subjectAbbreviation, courseNumber, formattedCourse, terms, null, null);
		List<PlanStudentStatusTO> studentStatuses = planService.getPlanStudentStatusByCourse(form);
		
		Map<String, PlanStudentStatusByCourseTO> courses = new HashMap<String, PlanStudentStatusByCourseTO>();
		List<String> uniqueStudents = new ArrayList<String>();
		for(PlanStudentStatusTO studentStatus:studentStatuses){
			if(courses.containsKey(studentStatus.getFormattedCourse().trim()))
			{
				PlanStudentStatusByCourseTO course = courses.get(studentStatus.getFormattedCourse().trim());
				course.addStudentStatus(studentStatus);
				if(!uniqueStudents.contains(studentStatus.getStudentId()))
					uniqueStudents.add(studentStatus.getStudentId());
			}else{
				PlanStudentStatusByCourseTO course = new PlanStudentStatusByCourseTO(studentStatus.getFormattedCourse(), studentStatus.getCourseTitle());
				course.addStudentStatus(studentStatus);
				courses.put(studentStatus.getFormattedCourse(), course);
				if(!uniqueStudents.contains(studentStatus.getStudentId()))
					uniqueStudents.add(studentStatus.getStudentId());
			}
		}
			
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("totalUniqueCourses", new Integer(courses.size()));
		parameters.put("totalUniqueStudents", new Integer(uniqueStudents.size()));
		List<PlanStudentStatusByCourseTO> courseList = Lists.newArrayList(courses.values());
		Collections.sort(courseList, PlanStudentStatusByCourseTO.FORMATTED_COURSE_COMPARATOR);
		SearchParameters.addTermsToMap(terms , parameters);
		SearchParameters.addPlanSearchForm(form, parameters);
		generateReport(response,  parameters, courseList,  REPORT_URL_NUMBER_STUDENTS_BY_STATUS, 
				 reportType, REPORT_FILE_TITLE_NUMBER_STUDENTS_BY_STATUS);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
