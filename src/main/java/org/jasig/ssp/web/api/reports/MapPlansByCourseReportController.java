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
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/1/report/map/numbercourses")
public class MapPlansByCourseReportController extends ReportBaseController<PlanCourseCountTO> {

	private static String REPORT_URL_PDF = "/reports/numberPlansByCourse.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_COURSES_IN_PLAN = "Number_Of_Plans_By_Course";

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
	void getNumberOfPlansByCourse(
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

		SearchPlanTO form = new SearchPlanTO(planStatus, subjectAbbreviation, courseNumber, formattedCourse, terms, null, null);
		List<PlanCourseCountTO> counts = planService.getPlanCountByCourse(form);

		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addTermsToMap( terms, parameters);
		SearchParameters.addPlanSearchForm(form, parameters);


		renderReport( response,  parameters, counts,  REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null,
				reportType, REPORT_FILE_TITLE_NUMBER_COURSES_IN_PLAN);
	}

	@Override
	protected boolean overridesCsvRendering() {
		return true;
	}

	@Override
	public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<PlanCourseCountTO> reportResults,
								 String reportViewUrl, String reportType, String reportName,
								 AbstractCsvWriterHelper csvHelper) {
		return new String[] {
				"COURSE",
				"COURSE_TITLE",
				"PLAN_COUNT"
		};
	}

	@Override
	public List<String[]> csvBodyRows(PlanCourseCountTO reportResultElement, Map<String, Object> reportParameters,
							   Collection<PlanCourseCountTO> reportResults, String reportViewUrl, String reportType, String reportName,
							   AbstractCsvWriterHelper csvHelper) {
		return csvHelper.wrapCsvRowInList(new String[] {
				reportResultElement.getFormattedCourse(),
				reportResultElement.getCourseTitle(),
				csvHelper.formatLong(reportResultElement.getStudentCount())
		});
	}
}
