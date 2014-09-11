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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusByCourseTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/1/report/map/numberstudentsbystatus")
public class MapStudentsByStatusReportController extends ReportBaseController<PlanStudentStatusByCourseTO> {

	private static String REPORT_URL_PDF = "/reports/numberStudentsByStatus.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_STUDENTS_BY_STATUS = "Number_Of_Students_By_Status";

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

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	public @ResponseBody
	void getNumberOfStudentsByStatus(
			final HttpServletResponse response,
			final @RequestParam(required = false) String courseNumber,
			final @RequestParam(required = false) String subjectAbbreviation,
			final @RequestParam(required = false) String formattedCourse,
			final @RequestParam(required = false) PlanStatus planStatus,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

		List<Term> terms = null;
		if(!StringUtils.isEmpty(termCode))
			terms = SearchParameters.getTerms(Lists.newArrayList(termCode), termService);

		SearchPlanTO form = new SearchPlanTO(null, subjectAbbreviation, courseNumber, formattedCourse, terms, null, null);
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
		renderReport(response,  parameters, courseList,  REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null,
				reportType, REPORT_FILE_TITLE_NUMBER_STUDENTS_BY_STATUS);
	}

	@Override
	protected boolean overridesCsvRendering() {
		return true;
	}

	@Override
	public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<PlanStudentStatusByCourseTO> reportResults,
								 String reportViewUrl, String reportType, String reportName,
								 AbstractCsvWriterHelper csvHelper) {
		return new String[] {
				"COURSE",
				"COURSE_TITLE",
				"STUDENT_ID",
				"PLAN_STATUS",
				"PLAN_STATUS_DETAILS"
		};
	}

	@Override
	public List<String[]> csvBodyRows(PlanStudentStatusByCourseTO reportResultElement, Map<String, Object> reportParameters,
							   Collection<PlanStudentStatusByCourseTO> reportResults, String reportViewUrl, String reportType, String reportName,
							   AbstractCsvWriterHelper csvHelper) {
		// take a copy b/c we know the list is rebuilt on every getter invocation
		final List<PlanStudentStatusTO> studentStatuses = reportResultElement.getStudentStatusByCourse();
		final List<String[]> csvRows = Lists.newArrayListWithCapacity(studentStatuses.size());
		for (  PlanStudentStatusTO studentStatus :  studentStatuses ) {
			csvRows.add(new String[] {
					reportResultElement.getFormattedCourse(),
					reportResultElement.getCourseTitle(),
					studentStatus.getStudentId(),
					// The PDF report renders 'No Status' in this case b/c it just looks better. Chose to output an
					// empty field for CSV b/c that's fundamentally about data, and the data reality here is
					// a nullity, not a special 'No Status' status. A flimsy justification, to be sure, but that's
					// why CSV and PDF don't behave identically
					studentStatus.getPlanStatus() == null ? null : studentStatus.getPlanStatus().getDisplayName(),
					studentStatus.getStatusDetails()
			});
		}
		return csvRows;
	}

}
