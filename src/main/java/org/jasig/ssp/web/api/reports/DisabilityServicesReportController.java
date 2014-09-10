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

import com.google.common.collect.Maps;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.jasig.ssp.service.reference.DisabilityTypeService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>report/AddressLabels</code>
 */
@Controller
@RequestMapping("/1/report/disabilityservices")
public class DisabilityServicesReportController extends ReportBaseController<DisabilityServicesReportTO> { // NOPMD

	private static String REPORT_URL_PDF = "/reports/disabilityServices.jasper";
	private static String REPORT_FILE_TITLE = "Disability_Services_Report";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressLabelsReportController.class);

	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient PersonTOFactory personTOFactory;
	@Autowired
	private transient SpecialServiceGroupService ssgService;
	@Autowired
	private transient ReferralSourceService referralSourcesService;
	@Autowired
	private transient RegistrationStatusByTermService registrationStatusByTermService;
	@Autowired
	private transient TermService termService;
	@Autowired
	private transient ProgramStatusService programStatusService;
	@Autowired
	protected transient StudentTypeService studentTypeService;	
	
	@Autowired
	protected transient ServiceReasonService serviceReasonService;	
	
	@Autowired
	protected transient DisabilityStatusService disabilityStatusService;	
	
	@Autowired
	protected transient DisabilityTypeService disabilityTypeService;

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
	@PreAuthorize("(hasRole('ROLE_REPORT_READ') and hasRole('ROLE_ACCOMMODATION_READ'))")
	@ResponseBody
	public void getDisabilityServicesReport(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = true) UUID coachId,	
			final @RequestParam(required = false) UUID odsCoachId,
			final @RequestParam(required = false) UUID disabilityStatusId,
			final @RequestParam(required = false) UUID disabilityTypeId,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> referralSourcesIds,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) Integer anticipatedStartYear,
			final @RequestParam(required = false) String anticipatedStartTerm,
			final @RequestParam(required = false) Integer actualStartYear,
			final @RequestParam(required = false) String actualStartTerm,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) String rosterStatus,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final PersonSearchFormTO personSearchForm = new PersonSearchFormTO();
		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);
		SearchParameters.addOdsCoach(odsCoachId, parameters, personSearchForm, personService, personTOFactory);

		SearchParameters.addReferenceLists(studentTypeIds, 
				specialServiceGroupIds, 
				referralSourcesIds,
				serviceReasonIds,
				parameters, 
				personSearchForm, 
				studentTypeService, 
				ssgService, 
				referralSourcesService,
				serviceReasonService);
		
		
		SearchParameters.addDateRange(createDateFrom, 
				createDateTo, 
				termCode, 
				parameters, 
				personSearchForm, 
				termService);
		
		SearchParameters.addReferenceTypes(programStatus, 
				disabilityStatusId, 
				disabilityTypeId,
				true,
				rosterStatus,
				homeDepartment,
				parameters, 
				personSearchForm, 
				programStatusService, 
				disabilityStatusService,
				disabilityTypeService);
		
		SearchParameters.addAnticipatedAndActualStartTerms(anticipatedStartTerm, 
				anticipatedStartYear, 
				actualStartTerm, 
				actualStartYear, 
				parameters, 
				personSearchForm);

		// TODO Specifying person name sort fields in the SaP doesn't seem to
		// work... end up with empty results need to dig into actual query
		// building
		final PagingWrapper<DisabilityServicesReportTO> people = personService.getDisabilityReport(
				personSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status));
		List<DisabilityServicesReportTO> compressedReports = this.processStudentReportTOs(people);

		SearchParameters.addStudentCount(compressedReports, parameters);

		renderReport(response,
				parameters, 
				compressedReports,
				REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null,
				reportType, 
				REPORT_FILE_TITLE);

	}

	@Override
	protected boolean overridesCsvRendering() {
		return true;
	}

	@Override
	public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<DisabilityServicesReportTO> reportResults,
								 String reportViewUrl, String reportType, String reportName,
								 AbstractCsvWriterHelper csvHelper) {
		return new String[] {
				"STUDENT_FIRST_NAME",
				"STUDENT_MIDDLE_NAME",
				"STUDENT_LAST_NAME",
				"STUDENT_ID",
				"ILP",
				"DISABILITY_TYPES",
				"PROGRAM_STATUS",
				"ODS_STATUS",
				"INELIGIBILITY",
				"ODS_REGISTRATION_DATE",
				"REGISTERED_IN_CURRENT_TERM",
				"MAJOR",
				"VETERAN_STATUS",
				"ETHNICITY",
				"ASSIGNMENT_DATES",
				"AGENCY_CONTACTS",
				"COACH_FIRST_NAME",
				"COACH_LAST_NAME"
		};
	}

	@Override
	public List<String[]> csvBodyRows(DisabilityServicesReportTO reportResultElement, Map<String, Object> reportParameters,
							   Collection<DisabilityServicesReportTO> reportResults, String reportViewUrl, String reportType, String reportName,
							   AbstractCsvWriterHelper csvHelper) {
		return csvHelper.wrapCsvRowInList(new String[] {
				reportResultElement.getFirstName(),
				reportResultElement.getMiddleName(),
				reportResultElement.getLastName(),
				reportResultElement.getSchoolId(),
				csvHelper.formatFriendlyBoolean(reportResultElement.getIsIlp(), false),
				reportResultElement.getdisabilityTypesName(),
				reportResultElement.getCurrentProgramStatusName(),
				reportResultElement.getOdsStatus(),
				reportResultElement.getOdsReason(),
				reportResultElement.getOdsRegistrationDateString(),
				csvHelper.formatIntegerAsFriendlyBoolean(reportResultElement.getRegistrationStatus(), 0, false),
				reportResultElement.getMajor(),
				reportResultElement.getVeteranStatus(),
				reportResultElement.getEthnicity(),
				reportResultElement.getAssignmentDates(),
				reportResultElement.getAgencyContacts(),
				reportResultElement.getCoachFirstName(),
				reportResultElement.getCoachLastName()
		});
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}