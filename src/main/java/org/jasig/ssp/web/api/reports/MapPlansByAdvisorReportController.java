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

import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRException;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.DateTerm;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/1/report/map/numberplansbyadvisor")
public class MapPlansByAdvisorReportController extends ReportBaseController<PlanAdvisorCountTO> {

	private static String REPORT_URL_NUMBER_PLANS_BY_ADVISOR = "/reports/numberPlansByAdvisor.jasper";
	private static String REPORT_FILE_TITLE_NUMBER_PLANS_BY_ADVISOR = "Number_Of_Plans_By_Advisor";

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
	void getNumberOfPlansByAdvisor(
			final HttpServletResponse response,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

		DateTerm dateTerm = new DateTerm(createDateFrom, createDateTo, termCode, termService);
		List<Term> terms = new ArrayList<Term>();
		if(dateTerm != null && dateTerm.getTerm() != null)
			terms.add(dateTerm.getTerm());
		SearchPlanTO form = new SearchPlanTO(null, null, null, null, terms, dateTerm.getStartDate(), dateTerm.getEndDate());
		List<PlanAdvisorCountTO> counts = planService.getAdvisorsPlanCount(form);

		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addPlanSearchForm(form, parameters);
		SearchParameters.addDateTermToMap(dateTerm, parameters);

		renderReport( response,  parameters, counts,  REPORT_URL_NUMBER_PLANS_BY_ADVISOR,
				reportType, REPORT_FILE_TITLE_NUMBER_PLANS_BY_ADVISOR);
	}

}
