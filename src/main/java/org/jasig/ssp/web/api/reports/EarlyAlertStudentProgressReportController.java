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
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentProgressReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
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
 * Service methods for Reporting on Early Alert Student Progress Report
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertstudentprogress</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertstudentprogress")
public class EarlyAlertStudentProgressReportController extends ReportBaseController<EarlyAlertStudentProgressReportTO> {

	private static final String REPORT_URL = "/reports/earlyAlertStudentProgressReport.jasper";
	private static final String REPORT_FILE_TITLE = "Early_Alert_Student_Progress_Report";
	private static final String INITIAL_TERM = "initialTerm";
	private static final String COMPARISON_TERM = "comparisonTerm";
	

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
	protected transient ServiceReasonService serviceReasonService;	
	
	@Autowired
	private transient SpecialServiceGroupService ssgService;
	@Autowired
	private transient ProgramStatusService programStatusService;	
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;
	
	@Autowired
	protected transient RegistrationStatusByTermService registrationStatusByTermService;
	

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
	public void getEarlyAlertStudentProgressReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) String rosterStatus,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) UUID coachId,	
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = true) String termCodeInitial,
			final @RequestParam(required = true) String termCodeComparitor,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final PersonSearchFormTO personSearchForm = new PersonSearchFormTO();
		
		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);
		
		SearchParameters.addReferenceLists(studentTypeIds, 
				specialServiceGroupIds, 
				null,
				serviceReasonIds,
				parameters, 
				personSearchForm, 
				studentTypeService, 
				ssgService, 
				null,
				serviceReasonService);
		
		
		SearchParameters.addReferenceTypes(programStatus, 
				null, 
				false,
				rosterStatus,
				homeDepartment,
				parameters, 
				personSearchForm, 
				programStatusService, 
				null);

		
		Term initialTerm = termService.getByCode(termCodeInitial);
		Term comparisonTerm = termService.getByCode(termCodeComparitor);

		
		final EarlyAlertStudentSearchTO initialSearchForm = new EarlyAlertStudentSearchTO(personSearchForm, 
				initialTerm.getStartDate(), initialTerm.getEndDate());

		final PagingWrapper<EarlyAlertStudentReportTO> initialPeopleInfo = earlyAlertService.getStudentsEarlyAlertCountSetForCritera(
				initialSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status, "person"));
		
		final EarlyAlertStudentSearchTO comparisonSearchForm = new EarlyAlertStudentSearchTO(personSearchForm, 
				comparisonTerm.getStartDate(), comparisonTerm.getEndDate());

		final PagingWrapper<EarlyAlertStudentReportTO> comparisonPeopleInfo = earlyAlertService.getStudentsEarlyAlertCountSetForCritera(
				comparisonSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status, "person"));
		
		
		List<EarlyAlertStudentReportTO> initialPeopleInfoCompressed = processReports(initialPeopleInfo, earlyAlertResponseService);
		List<EarlyAlertStudentReportTO> comparisonPeopleInfoCompressed = processReports(comparisonPeopleInfo, earlyAlertResponseService);
		
		List<EarlyAlertStudentProgressReportTO> people = new ArrayList<EarlyAlertStudentProgressReportTO>();
		for(EarlyAlertStudentReportTO initialPersonInfo : initialPeopleInfoCompressed){
			EarlyAlertStudentReportTO foundPerson = null;
			for(EarlyAlertStudentReportTO comparisonPersonInfo : comparisonPeopleInfoCompressed){
				if(initialPersonInfo.getId().equals(comparisonPersonInfo.getId())){
					foundPerson = comparisonPersonInfo;
					break;
				}
			}
			
			Long finalCount = foundPerson != null ? (Long)foundPerson.getTotal() : 0;
			if(!people.contains(initialPersonInfo)){
				RegistrationStatusByTerm comparativeTermRegistrationStatus = registrationStatusByTermService.getForTerm(initialPersonInfo.getSchoolId(), comparisonTerm.getCode());
				if(comparativeTermRegistrationStatus != null)
					initialPersonInfo.setRegistrationStatus(comparativeTermRegistrationStatus.getRegisteredCourseCount());
				else
					initialPersonInfo.setRegistrationStatus(0);
				people.add(new EarlyAlertStudentProgressReportTO(initialPersonInfo,initialPersonInfo.getTotal(), finalCount));
			}
				
		}
		
		parameters.put(INITIAL_TERM, initialTerm.getName());
		parameters.put(COMPARISON_TERM, comparisonTerm.getName());
		
		renderReport(response,  parameters, people,  REPORT_URL, reportType, REPORT_FILE_TITLE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}