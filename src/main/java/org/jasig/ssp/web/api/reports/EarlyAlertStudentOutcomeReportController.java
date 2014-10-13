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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentResponseOutcomeReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.PersonSearchFormTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.SortDirection;
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

/**
 * Service methods for Reporting on Early Alert Student Outcomes
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertstudentoutcome</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertstudentoutcome")
public class EarlyAlertStudentOutcomeReportController extends ReportBaseController<EarlyAlertStudentResponseOutcomeReportTO> {

	private static final String REPORT_URL = "/reports/earlyAlertStudentOutcomeReport.jasper";
	private static final String[] REPORT_FILE_TITLE = {"Early_Alert_Student_Outcome_Report", "Early_Alert_Student_Outreach_Report"};
	private static final String[] REPORT_TITLE = {"Early Alert Student Outcome Report", "Early Alert Student Outreach Report"};
	private static final String[] COLUMN_TITLE = {"Outcome(s)", "Outreach(s)"};
	private static final String[] DETAIL_COLUMN_TITLE = {"OUTCOME", "OUTREACH"};
	private static final String REPORT_TITLE_LABEL = "reportTitle";
	private static final String COLUMN_TITLE_LABEL = "columnTitle";
	private static final String DETAIL_COLUMN_TITLE_LABEL = "detailColumnTitle";
	private static final String EARLY_ALERT_OUTCOME = "earlyAlertOutcome";
	private static final String EARLY_ALERT_OUTREACH = "earlyAlertOutreachIds";
	private static final String SELECTED_OUTCOME_NAMES = "selectedOutcomeNames";
	
	private static final String OUTCOME_TOTALS = "outcomeTotals";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertStudentOutcomeReportController.class);

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
	private transient ProgramStatusService programStatusService;
	
	
	@Autowired
	protected transient EarlyAlertService earlyAlertService;
	
	@Autowired
	protected transient EarlyAlertResponseService earlyAlertResponseService;
	
	@Autowired
	protected transient EarlyAlertOutcomeService earlyAlertOutcomeService;
	
	@Autowired
	protected transient EarlyAlertOutreachService earlyAlertOutreachService;
	
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

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REPORT_READ)
	@ResponseBody
	public void getEarlyAlertStudentOutcomeReport(
			final HttpServletResponse response,
			final @RequestParam(required = true) String outcomeType,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) String rosterStatus,
			final @RequestParam(required = false) String homeDepartment,
			final @RequestParam(required = false) UUID coachId,	
			final @RequestParam(required = false) UUID watcherId,	
			final @RequestParam(required = false) List<UUID> studentTypeIds,
			final @RequestParam(required = false) List<UUID> serviceReasonIds,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
			final @RequestParam(required = false) List<UUID> outcomeIds,
            final @RequestParam(required = false) Date responseCreateDateFrom,
            final @RequestParam(required = false) Date responseCreateDateTo,
            final @RequestParam(required = false) String alertTermCode,
            final @RequestParam(required = false) Date alertCreateDateFrom,
            final @RequestParam(required = false) Date alertCreateDateTo,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {
		
		final Map<String, Object> parameters = Maps.newHashMap();
		final PersonSearchFormTO personSearchForm = new PersonSearchFormTO();

        DateTerm dateTerm = new DateTerm(alertCreateDateFrom, alertCreateDateTo, alertTermCode, termService);

        if ( StringUtils.isBlank(alertTermCode) || alertTermCode.trim().toLowerCase().equals("not used") && alertCreateDateFrom != null ) {
            dateTerm.setTerm(null);
        } else if (alertTermCode != null && alertCreateDateFrom == null) {
            dateTerm.setStartEndDates(null, null);
        }
		
		SearchParameters.addCoach(coachId, parameters, personSearchForm, personService, personTOFactory);
		
		SearchParameters.addWatcher(watcherId, parameters, personSearchForm, personService, personTOFactory);
		
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
		
		SearchParameters.addDateRange(alertCreateDateFrom,
                alertCreateDateTo,
                alertTermCode,
				parameters, 
				personSearchForm, 
				termService);

		// These params are added to the *person* filter, not the EA/EAR field, so make sure no term/date params are
		// set. (Unclear whether we could just skip this call altogether. Keeping it b/c it was here previously (albeit
		// with non-null date/term params being passed through), and b/c EarlyAlertStudentReportController also has it.)
		SearchParameters.addDateRange(null,
				null,
				null,
				parameters,
				personSearchForm,
				termService);
		
		SearchParameters.addReferenceTypes(programStatus, 
				null, 
				false,
				rosterStatus,
				homeDepartment,
				parameters, 
				personSearchForm, 
				programStatusService, 
				null);
		
		final List<UUID> cleanOutcomeIds = SearchParameters.cleanUUIDListOfNulls(outcomeIds);
		SearchParameters.addUUIDSToMap(SELECTED_OUTCOME_NAMES, "Not Used", cleanOutcomeIds, parameters, earlyAlertOutcomeService);

		final EarlyAlertStudentSearchTO searchForm = new EarlyAlertStudentSearchTO(personSearchForm,
                dateTerm.getTermCodeNullPossible(), dateTerm.getStartDate(), dateTerm.getEndDate(),
                responseCreateDateFrom, responseCreateDateTo);
		
		searchForm.setOutcomeIds(cleanOutcomeIds);
		
		List<Pair<String, SortDirection>> sortFields = Lists.newArrayList();
		sortFields.add(new Pair<String, SortDirection>("name", SortDirection.ASC));
		
		List<Pair<String,Long>> outcomeTotals = null;
		SortingAndPaging sAndP = new SortingAndPaging(status, sortFields, null, SortDirection.ASC);
		if(outcomeType.equals(EARLY_ALERT_OUTCOME)){
			outcomeTotals = getOutcomes(cleanOutcomeIds,  searchForm, sAndP);
		}else if(outcomeType.equals(EARLY_ALERT_OUTREACH)){
			outcomeTotals = getOutreaches(cleanOutcomeIds,  searchForm, sAndP);
		}
		
		// TODO Specifying person name sort fields in the SaP doesn't seem to
		// work... end up with empty results need to dig into actual query
		// building
		searchForm.setOutcomeIds(cleanOutcomeIds);
		final List<EarlyAlertStudentResponseOutcomeReportTO> reportTOs = earlyAlertResponseService.getEarlyAlertResponseOutcomeTypeForStudentsByCriteria(
				outcomeType, searchForm, SortingAndPaging.createForSingleSortAll(status, outcomeType + ".name", "ASC"));
		
	
		// Add a blank line to the table
		outcomeTotals.add(new Pair<String,Long>(" ", null));
		
		searchForm.setOutcomeIds(cleanOutcomeIds);
		outcomeTotals.add(
				new Pair<String,Long>("Total Early Alerts",earlyAlertResponseService.
						getEarlyAlertCountByOutcomeCriteria(searchForm)));
		
		SearchParameters.addDateTermToMap(dateTerm, parameters);
        SearchParameters.addResponseDateRangeToMap(responseCreateDateFrom, responseCreateDateTo, parameters);
		
		parameters.put(OUTCOME_TOTALS, outcomeTotals);
		
		
		
		Integer index = outcomeType.equals("earlyAlertOutcome") ? 0:1;
		parameters.put(REPORT_TITLE_LABEL, REPORT_TITLE[index]);
		parameters.put(COLUMN_TITLE_LABEL, COLUMN_TITLE[index]);
		parameters.put(DETAIL_COLUMN_TITLE_LABEL,DETAIL_COLUMN_TITLE[index]);
		
		renderReport(response,  parameters, reportTOs,  REPORT_URL, reportType, REPORT_FILE_TITLE[index]);
	}
	
	private List<Pair<String,Long>> getOutcomes(final List<UUID> cleanOutcomeIds, EarlyAlertStudentSearchTO searchForm, SortingAndPaging sAndP) throws ObjectNotFoundException{
		List<Pair<String,Long>> outcomeTotals = new ArrayList<Pair<String,Long>>();
		Collection<EarlyAlertOutcome> earlyAlertOutcomes;	

        if (cleanOutcomeIds != null && cleanOutcomeIds.size() > 0) {
			earlyAlertOutcomes = new ArrayList<EarlyAlertOutcome>();
			for (UUID outcomeId:cleanOutcomeIds) {
				earlyAlertOutcomes.add(earlyAlertOutcomeService.get(outcomeId));
			}
		} else {
			earlyAlertOutcomes = earlyAlertOutcomeService.getAll(sAndP).getRows();
		}
	
        for (EarlyAlertOutcome earlyAlertOutcome:earlyAlertOutcomes) {
            searchForm.setOutcomeIds(null);
            outcomeTotals.add(
                new Pair<String,Long>(earlyAlertOutcome.getName(),earlyAlertResponseService.
                        getEarlyAlertOutcomeTypeCountByCriteria(EARLY_ALERT_OUTCOME, earlyAlertOutcome.getId(), searchForm)));
        }
		return outcomeTotals;
	}
	
	private List<Pair<String,Long>> getOutreaches(final List<UUID> cleanOutcomeIds, EarlyAlertStudentSearchTO searchForm, SortingAndPaging sAndP) 
			throws ObjectNotFoundException{
		List<Pair<String,Long>> outcomeTotals = new ArrayList<Pair<String,Long>>();
		Collection<EarlyAlertOutreach> earlyAlertOutreaches = earlyAlertOutreachService.getAll(sAndP).getRows();
		
		searchForm.setOutcomeIds(cleanOutcomeIds);
		
		for (EarlyAlertOutreach earlyAlertOutreach:earlyAlertOutreaches) {
			outcomeTotals.add(
					new Pair<String,Long>(earlyAlertOutreach.getName(),earlyAlertResponseService.
							getEarlyAlertOutcomeTypeCountByCriteria(EARLY_ALERT_OUTREACH, earlyAlertOutreach.getId(), searchForm)));
		}
		return outcomeTotals;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	
}