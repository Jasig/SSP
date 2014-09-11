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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.BaseStudentReportTO;
import org.jasig.ssp.transferobject.reports.JournalStepSearchFormTO;
import org.jasig.ssp.transferobject.reports.JournalStepStudentReportTO;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/1/report/journalsessiondetail")
public class JournalSessionDetailsReportController extends ReportBaseController<JournalStepStudentReportTO> {

	private static String REPORT_URL_PDF = "/reports/journalSessionDetailReport.jasper";
	private static String REPORT_FILE_TITLE = "Journal_Step_Details_Report";
	private static String JOURNAL_SESSION_DETAILS = "journalSessionDetails";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalSessionDetailsReportController.class);

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
	protected transient JournalStepDetailService journalEntryStepDetailService;	
	
	@Autowired
	protected transient JournalEntryService journalEntryService;	

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
	public void getJournalSessionDetails(
			final HttpServletResponse response,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) UUID coachId,
			final @RequestParam(required = false) UUID watcherId,
			final @RequestParam(required = false) UUID programStatus,	
			final @RequestParam(required = false) Boolean hasStepDetails,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) List<UUID> journalStepDetailIds,
			final @RequestParam(required = false) Date createDateFrom,
			final @RequestParam(required = false) Date createDateTo,
			final @RequestParam(required = false) String termCode,
            final @RequestParam(required = false) String actualStartTerm,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final JournalStepSearchFormTO personSearchForm = new JournalStepSearchFormTO();
		
		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);
		
		SearchParameters.addWatcher(watcherId, parameters, personSearchForm, personService, personTOFactory);

        if ( StringUtils.isNotBlank(actualStartTerm) ) {
            SearchParameters.addactualStartTerm(actualStartTerm, null, parameters);
            personSearchForm.setActualStartTerm(actualStartTerm);
        }
		
		SearchParameters.addReferenceLists(studentTypeIds, 
				specialServiceGroupIds, 
				null,
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

		List<UUID> cleanJournalStepDetailIds = SearchParameters.cleanUUIDListOfNulls(journalStepDetailIds);
		SearchParameters.addUUIDSToMap(JOURNAL_SESSION_DETAILS, 
				SearchParameters.ALL, 
				cleanJournalStepDetailIds, 
				parameters, 
				journalEntryStepDetailService);
		
		
		if((cleanJournalStepDetailIds == null || cleanJournalStepDetailIds.isEmpty())){
			cleanJournalStepDetailIds = new ArrayList<UUID>();
			PagingWrapper<JournalStepDetail> details = journalEntryStepDetailService.getAll(SortingAndPaging.createForSingleSortAll(status, "sortOrder", "ASC"));
			for(JournalStepDetail detail:details){
				cleanJournalStepDetailIds.add(detail.getId());
			}
		}
		personSearchForm.setJournalStepDetailIds(cleanJournalStepDetailIds);
		
		personSearchForm.setHasStepDetails(hasStepDetails);
		
		final List<JournalStepStudentReportTO> reports = (List<JournalStepStudentReportTO>)
				Lists.newArrayList(journalEntryService.getJournalStepStudentReportTOsFromCriteria(
				personSearchForm, SearchParameters.getReportPersonSortingAndPagingAll(status,"person")));
		
		SearchParameters.addStudentCount(reports, parameters);
		renderReport(response, parameters, reports, REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null, reportType, REPORT_FILE_TITLE);

	}

	@Override
	protected boolean overridesCsvRendering() {
		return true;
	}

	@Override
	public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<JournalStepStudentReportTO> reportResults,
								 String reportViewUrl, String reportType, String reportName, AbstractCsvWriterHelper csvHelper) {
		return new String[] {
				"STUDENT_ID",
				"STUDENT_FIRST_NAME",
				"STUDENT_MIDDLE_NAME",
				"STUDENT_LAST_NAME",
				"COACH_FIRST_NAME",
				"COACH_LAST_NAME",
				"JOURNAL_DETAIL"
		};
	}

	@Override
	public List<String[]> csvBodyRows(JournalStepStudentReportTO reportResultElement, Map<String, Object> reportParameters,
							   Collection<JournalStepStudentReportTO> reportResults, String reportViewUrl,
							   String reportType, String reportName, AbstractCsvWriterHelper csvHelper) {
		return csvHelper.wrapCsvRowInList(new String[] {
				reportResultElement.getSchoolId(),
				reportResultElement.getFirstName(),
				reportResultElement.getMiddleName(),
				reportResultElement.getLastName(),
				reportResultElement.getCoachFirstName(),
				reportResultElement.getCoachLastName(),
				reportResultElement.getJournalStepDetailName()
		});
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}


}
