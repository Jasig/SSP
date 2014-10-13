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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
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

import com.google.common.collect.Maps;

/**
 * Service methods for Reporting on Early Alert Student Outreach
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertstudentoutreach</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertstudentoutreach")
public class EarlyAlertStudentOutreachReportController extends ReportBaseController<EarlyAlertStudentOutreachReportTO> {

	private static final String REPORT_URL = "/reports/earlyAlertStudentOutreachReport.jasper";
	private static final String REPORT_FILE_TITLE = "Early_Alert_Student_Outreach_Report";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertStudentOutreachReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient TermService termService;
	@Autowired
	private transient PersonTOFactory personTOFactory;	
	@Autowired
	private transient ReferralSourceService referralSourcesService;
	@Autowired
	private transient ProgramStatusService programStatusService;
	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;
	
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void getEarlyAlertStudentOutreachReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) String rosterStatus,
			final @RequestParam(required = false) List<UUID> earlyAlertOutcomes,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) String homeDepartment,
            final @RequestParam(required = false) Date responseCreateDateFrom,
            final @RequestParam(required = false) Date responseCreateDateTo,
            final @RequestParam(required = false) String alertTermCode,
            final @RequestParam(required = false) Date alertCreateDateFrom,
            final @RequestParam(required = false) Date alertCreateDateTo,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

        DateTerm dateTerm = new DateTerm(alertCreateDateFrom, alertCreateDateTo, alertTermCode, termService);

        if ( StringUtils.isBlank(alertTermCode) || alertTermCode.trim().toLowerCase().equals("not used") && alertCreateDateFrom != null ) {
            dateTerm.setTerm(null);
        } else if (alertTermCode != null && alertCreateDateFrom == null) {
            dateTerm.setStartEndDates(null, null);
        }

		final List<UUID> earlyAlertOutcomesClean = SearchParameters.cleanUUIDListOfNulls(earlyAlertOutcomes);
		
		final Collection<EarlyAlertStudentOutreachReportTO> outreachOutcomes = earlyAlertResponseService.getEarlyAlertOutreachCountByOutcome(
                dateTerm.getTermCodeNullPossible(),
                dateTerm.getStartDate(),
				dateTerm.getEndDate(),
                responseCreateDateFrom,
                responseCreateDateTo,
				earlyAlertOutcomesClean,
				rosterStatus,
				null);
		
		final Map<String, Object> parameters = Maps.newHashMap();
		SearchParameters.addEarlyAlertOutcomesToMap(earlyAlertOutcomesClean, parameters, earlyAlertOutcomeService);
		SearchParameters.addDateTermToMap(dateTerm, parameters);
        SearchParameters.addResponseDateRangeToMap(responseCreateDateFrom, responseCreateDateTo, parameters);
        SearchParameters.addHomeDepartmentToMap(homeDepartment, parameters);


		renderReport(response,  parameters, outreachOutcomes,  REPORT_URL, reportType, REPORT_FILE_TITLE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}