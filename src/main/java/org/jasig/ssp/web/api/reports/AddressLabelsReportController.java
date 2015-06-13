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

package org.jasig.ssp.web.api.reports; // NOPMD

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.*;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
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
import com.google.common.collect.Maps;


/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>report/AddressLabels</code>
 */
@Controller
@RequestMapping("/1/report/AddressLabels")
public class AddressLabelsReportController extends ReportBaseController<BaseStudentReportTO> { // NOPMD

	private static String REPORT_URL = "/reports/generalStudentReport.jasper";
	private static String REPORT_FILE_TITLE = "General_Student_Report";
	
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
	private transient TermService termService;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	protected transient StudentTypeService studentTypeService;	

	@Autowired
	protected transient ServiceReasonService serviceReasonService;

	@Autowired
	protected transient ExternalStudentAcademicProgramService externalStudentAcademicProgramService;

	@Autowired
	protected transient ExternalStudentTranscriptService externalStudentTranscriptService;

	@Autowired
    protected transient ConfigService configurationService;

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
	public void getAddressLabels(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) UUID coachId,		
			final @RequestParam(required = false) UUID watcherId,			
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
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {

		final Map<String, Object> parameters = Maps.newHashMap();
		final PersonSearchFormTO personSearchForm = new PersonSearchFormTO();

		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);

		SearchParameters.addWatcher(watcherId, parameters, personSearchForm, personService, personTOFactory);

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
				null,
				false,
				null,
				homeDepartment,
				parameters,
				personSearchForm,
				programStatusService,
				null);

		SearchParameters.addAnticipatedAndActualStartTerms(anticipatedStartTerm,
				anticipatedStartYear,
				actualStartTerm,
				actualStartYear,
				parameters,
				personSearchForm);

		final PagingWrapper<BaseStudentReportTO> people = personService.getStudentReportTOsFromCriteria(
				personSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status));

        final String[] phoneOrderList = configurationService.getByNameNullOrDefaultValue("phone_display_order").trim().split(",");

        final Map<String, BaseStudentReportTO> compressedReportMap = processStudentReportTOsAsMap(people, phoneOrderList);
		final List<String> schoolIdKeys = new ArrayList<String>(compressedReportMap.keySet());
		final List<ExternalStudentAcademicProgram> academicPrograms = externalStudentAcademicProgramService.getBatchedAcademicProgramsBySchoolIds(schoolIdKeys);
		final List<ExternalStudentTranscript> transcripts = externalStudentTranscriptService.getBatchedRecordsBySchoolIds(schoolIdKeys);

		for ( ExternalStudentTranscript transcript : transcripts ) {
			compressedReportMap.get(transcript.getSchoolId()).setCumalativeGpaAndAcademicStanding(transcript);
		}

		for (ExternalStudentAcademicProgram academicProgram : academicPrograms) {
			compressedReportMap.get(academicProgram.getSchoolId()).addAcademicProgram(academicProgram);
		}

        parameters.put("studentCount", compressedReportMap == null ? 0 : compressedReportMap.size());
		renderReport(response, parameters, compressedReportMap.values(), REPORT_URL, reportType, REPORT_FILE_TITLE);
	}

	/**
	 * Semi-override of method in ReportBaseController to eliminate multiple loops through ReportTOs
	 * @param people
	 * @return
	 */
	private Map<String, BaseStudentReportTO> processStudentReportTOsAsMap(PagingWrapper<BaseStudentReportTO> people, final String[] phoneOrderConfig) {
		Map<String, BaseStudentReportTO> compressedReportMap = Maps.newHashMap();
		if (people == null || people.getResults() <= 0) {
			return compressedReportMap;
		}

		for (BaseStudentReportTO reportTO : new ArrayList<BaseStudentReportTO>(people.getRows())) {
            if (compressedReportMap.containsKey(reportTO.getSchoolId())) {
				compressedReportMap.get(reportTO.getSchoolId()).processDuplicate(reportTO);
			} else {
				reportTO.normalize();
                reportTO.setSinglePhoneNumberForReport(setPhoneNumberToDisplay(phoneOrderConfig, reportTO));
				compressedReportMap.put(reportTO.getSchoolId(), reportTO);
			}
		}
		return compressedReportMap;
	}

    private String setPhoneNumberToDisplay (final String[] phoneOrderList, final BaseStudentReportTO studentReportTO) {
        for (int index=0; index < phoneOrderList.length; index++) {
            if (phoneOrderList[index].equals("home") && StringUtils.isNotBlank(studentReportTO.getHomePhone())) {
                return studentReportTO.getHomePhone() + " (H)";
            } else if (phoneOrderList[index].equals("cell") && StringUtils.isNotBlank(studentReportTO.getCellPhone())) {
                return studentReportTO.getCellPhone() + " (C)";
            } else if (phoneOrderList[index].equals("work") && StringUtils.isNotBlank(studentReportTO.getWorkPhone())) {
                return studentReportTO.getWorkPhone() + " (P)";
            } else if (phoneOrderList[index].equals("alternate") && StringUtils.isNotBlank(studentReportTO.getAlternatePhone())) {
                return studentReportTO.getAlternatePhone() + " (A)";
            }
        }
        return "";
    }

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
