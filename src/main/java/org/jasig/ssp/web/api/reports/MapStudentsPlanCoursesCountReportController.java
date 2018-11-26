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
package org.jasig.ssp.web.api.reports;

import com.google.common.collect.Maps;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.reports.PlanStudentCoursesCountTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/1/report/map/studentplannedcoursescount")
public class MapStudentsPlanCoursesCountReportController extends ReportBaseController<PlanStudentCoursesCountTO> {

	private static String REPORT_URL_PDF = "/reports/studentPlannedCoursesCountReport.jasper";
	private static String REPORT_FILE_TITLE_STUDENTS_PLANNED_COURSES_COUNT = "Students_Planned_Courses_Count_Report";

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
	void getStudentPlannedCoursesCount(
			final HttpServletResponse response,
			final @RequestParam(required = false) String courseNumber,
			final @RequestParam(required = false) String subjectAbbreviation,
			final @RequestParam(required = false) List<String> termCodes,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

		List<Term> terms = SearchParameters.getTerms(termCodes, termService);

		SearchPlanTO form = new SearchPlanTO(null, subjectAbbreviation, courseNumber, null, terms, null, null);
		Collection<PlanStudentCoursesCountTO> planStudentCoursesCountTOs = planService.getPlanStudentCoursesCount(form);

		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("totalStudents", new Integer(planStudentCoursesCountTOs.size()));
		SearchParameters.addTermsToMap(terms , parameters);
		SearchParameters.addPlanSearchForm(form, parameters);
		renderReport(response,  parameters, planStudentCoursesCountTOs,  REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null,
				reportType, REPORT_FILE_TITLE_STUDENTS_PLANNED_COURSES_COUNT);
	}
}
