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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
 * Service methods for Reporting on Early Alert Student Referrals
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertstudentreferral</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertstudentreferral")
public class EarlyAlertStudentReferralReportController extends ReportBaseController<EarlyAlertStudentReportTO> {

	private static final String REPORT_URL = "/reports/earlyAlertStudentReferralReport.jasper";
	private static final String REPORT_FILE_TITLE = "Early_Alert_Student_Referral_Report";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressLabelsReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient TermService termService;
	@Autowired
	private transient EarlyAlertReferralService earlyAlertReferralsService;
	@Autowired
	private transient ProgramStatusService programStatusService;	
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;

	// @Autowired
	// private transient PersonTOFactory factory;

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
	@ResponseBody
	public void getEarlyAlertStudentReferralReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) String rosterStatus,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) UUID coachId,		
			final @RequestParam(required = false) UUID watcherId,			
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) UUID earlyAlertReferralId,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		
		PersonTO coachTO = SearchParameters.getPerson(coachId, personService, personTOFactory);

		PersonTO watcherTO = SearchParameters.getPerson(watcherId, personService, personTOFactory);

		
		DateTerm termDate =  new DateTerm(createDateFrom,  createDateTo, termCode, termService);	

		final PersonSearchFormTO searchForm = new PersonSearchFormTO(
				coachTO,
				programStatus, null, null, null, null,
				null, null,
				null);
		
		searchForm.setRosterStatus(rosterStatus);
		searchForm.setHomeDepartment(homeDepartment);
		searchForm.setWatcher(watcherTO);
		// TODO Specifying person name sort fields in the SaP doesn't seem to
		// work... end up with empty results need to dig into actual query
		// building
		final List<EarlyAlertStudentReportTO> peopleInfo = earlyAlertResponseService.getPeopleByEarlyAlertReferralIds(
				Arrays.asList(earlyAlertReferralId), 
				termDate.getStartDate(), 
				termDate.getEndDate(), 
				searchForm, 
				SearchParameters.getReportPersonSortingAndPagingAll(status));

		final Map<String, Object> parameters = Maps.newHashMap();
		
		SearchParameters.addReportDateToMap(parameters);
		SearchParameters.addCoachNameToMap(coachTO, parameters);
		SearchParameters.addRosterStatusToMap(rosterStatus, parameters);
		SearchParameters.addHomeDepartmentToMap(homeDepartment, parameters);
		SearchParameters.addDateTermToMap(termDate, parameters);
		SearchParameters.addProgramStatusToMap(programStatus, parameters, programStatusService);
		SearchParameters.addEarlyAlertReferralToMap(earlyAlertReferralId, parameters, earlyAlertReferralsService);
		List<EarlyAlertStudentReportTO> processedPeople = processReports(peopleInfo);
		SearchParameters.addStudentCount(processedPeople, parameters);
		renderReport( response,  parameters, processedPeople,  REPORT_URL,
				 reportType, REPORT_FILE_TITLE);
	}
	
	
	protected List<EarlyAlertStudentReportTO> processReports(List<EarlyAlertStudentReportTO> reports){
		 
		ArrayList<EarlyAlertStudentReportTO> compressedReports = new ArrayList<EarlyAlertStudentReportTO>();
		if(reports == null || reports.size() <= 0)
			return compressedReports;
		
		for(EarlyAlertStudentReportTO reportTO: reports){
			Integer index = compressedReports.indexOf(reportTO);
			if(index != null && index >= 0)
			{
				BaseStudentReportTO compressedReportTo = compressedReports.get(index);
				compressedReportTo.processDuplicate(reportTO);
			}else{
				compressedReports.add(reportTO);
			}
		}
		
		return compressedReports;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}