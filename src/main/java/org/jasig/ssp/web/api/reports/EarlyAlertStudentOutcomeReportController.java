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
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.AddressLabelSearchTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutcomeReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
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
 * Service methods for Reporting on Early Alert Student Outcomes
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertstudentoutcome</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertstudentoutcome")
public class EarlyAlertStudentOutcomeReportController extends EarlyAlertReportBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressLabelsReportController.class);

	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient TermService termService;
	
	@Autowired
	private transient PersonTOFactory personTOFactory;
	
	@Autowired
	private transient StudentTypeService studentTypeService;
	@Autowired
	private transient ProgramStatusService programStatusService;	
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;
	@Autowired
	private transient SpecialServiceGroupService ssgService;

	// @Autowired
	// private transient PersonTOFactory factory;

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
				Locale.US);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	@ResponseBody
	public void getEarlyAlertStudentOutcomeReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) UUID coachId,	
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false, defaultValue = "pdf") String reportType)
			throws ObjectNotFoundException, JRException, IOException {
		
		final PersonTO coachTO = getPerson(coachId, personService, personTOFactory);
		
		final DateTerm dateTerm =  new DateTerm(createDateFrom,  createDateTo, termCode, termService);
		
		final AddressLabelSearchTO addressLabelSearchTO = new AddressLabelSearchTO(
				coachTO,
				programStatus, specialServiceGroupIds, null, null, null,
				studentTypeIds, null,
				null);
		
		final EarlyAlertStudentSearchTO searchForm = new EarlyAlertStudentSearchTO(addressLabelSearchTO, 
				dateTerm.getStartDate(), dateTerm.getEndDate());

		// TODO Specifying person name sort fields in the SaP doesn't seem to
		// work... end up with empty results need to dig into actual query
		// building
		final PagingWrapper<EarlyAlertStudentReportTO> peopleInfo = earlyAlertService.getStudentsEarlyAlertCountSetForCritera(
				searchForm, SortingAndPaging.createForSingleSort(status, null,
						null, null, null, null));

		List<EarlyAlertStudentOutcomeReportTO> people = new ArrayList<EarlyAlertStudentOutcomeReportTO>();
		for(EarlyAlertStudentReportTO personInfo : peopleInfo.getRows()){
			people.add(new EarlyAlertStudentOutcomeReportTO(personInfo.getPerson(), personInfo.getTotal(), 0L, personInfo.getOpen(), 0L, 0L));
		}
		
		// final String programStatusName = ((null!=programStatus &&
		// !programStatus.isEmpty())?programStatus.get(0)():"");
		// Get the actual name of the UUID for the programStatus
		final String programStatusName = (programStatus == null ? ""
				: programStatusService.get(programStatus).getName());

		final Map<String, Object> parameters = Maps.newHashMap();
		
		parameters.put("coachName", getFullName(coachTO));
			
		parameters.put("programStatus", programStatusName);
		parameters.put("studentType", concatStudentTypesFromUUIDs(studentTypeIds, 
				studentTypeService));
		parameters.put("specialServiceGroupNames", concatSpecialGroupsNameFromUUIDs(specialServiceGroupIds, ssgService));
		
		
		parameters.put("reportDate", new Date());	
		
		setDateTermToMap(parameters, dateTerm);
		
		generateReport( response,  parameters, people,  "/reports/earlyAlertStudentOutcomeReport.jasper", 
				 reportType, "Early_Alert_Student_Outcome_Report");
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}