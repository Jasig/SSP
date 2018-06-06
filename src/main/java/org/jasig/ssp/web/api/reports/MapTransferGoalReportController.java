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
package org.jasig.ssp.web.api.reports;

import com.google.common.collect.Maps;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.external.ExternalCatalogYear;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalCatalogYearService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.TransferGoalService;
import org.jasig.ssp.transferobject.reports.MapTransferGoalReportTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/1/report/map/transfergoals")
public class MapTransferGoalReportController extends ReportBaseController<MapTransferGoalReportTO> {

	private static String REPORT_URL_PDF = "/reports/transferGoalsReport.jasper";
	private static String REPORT_FILE_TITLE_TRANSFER_GOALS = "Transfer_Goals_Report";

	@Autowired
	protected transient ProgramStatusService programStatusService;

	@Autowired
	protected transient TransferGoalService transferGoalService;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient PlanService planService;

	@Autowired
	protected transient ExternalCatalogYearService externalCatalogYearService;

	@Autowired
	private transient PersonTOFactory personTOFactory;

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
	void getTransferGoals(
			final HttpServletResponse response,
			final @RequestParam(required = false) List<UUID> transferGoalIds,
			final @RequestParam(required = false) List<UUID> planOwnerIds,
			final @RequestParam(required = false) UUID programStatus,
			final @RequestParam(required = false) String planExists,
			final @RequestParam(required = false) String catalogYearCode,
			final @RequestParam(required = false) Date modifiedDateFrom,
			final @RequestParam(required = false) Date modifiedDateTo,
			final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
			throws ObjectNotFoundException, IOException {


		final Map<String, Object> parameters = Maps.newHashMap();

		SearchParameters.addTransferGoalsToMap(transferGoalIds, parameters, transferGoalService);
		SearchParameters.addCreatedByUsersToMap(planOwnerIds, parameters, personService, personTOFactory);
		SearchParameters.addProgramStatusToMap(programStatus, parameters, programStatusService);
		SearchParameters.addPlanExistsToMap(planExists, parameters);
		SearchParameters.addCatalogYearToMap(catalogYearCode, parameters, externalCatalogYearService);
		SearchParameters.addStartEndDates(modifiedDateFrom, modifiedDateTo, parameters);

		Collection<MapTransferGoalReportTO> results = planService.getTransferGoalReport(transferGoalIds, planOwnerIds, programStatus,
				planExists, catalogYearCode, modifiedDateFrom, modifiedDateTo);
        List<ExternalCatalogYear> catalogYearList = externalCatalogYearService.getAll();
		Map<String, ExternalCatalogYear> catalogYears = getCatalogYears();
        for (MapTransferGoalReportTO to : results) {
            if (null!=catalogYears.get(to.getCatalogYearCode())) {
                to.setCatalogYearName(catalogYears.get(to.getCatalogYearCode()).getName());
            } else {
                to.setCatalogYearName(to.getCatalogYearCode());
            }
        }
		renderReport( response,  parameters, results,  REPORT_TYPE_PDF.equals(reportType) ? REPORT_URL_PDF : null,
				reportType, REPORT_FILE_TITLE_TRANSFER_GOALS);
	}

	private Map<String, ExternalCatalogYear> getCatalogYears() {
        Map<String, ExternalCatalogYear> catalogYears = Maps.newHashMap();
        for (ExternalCatalogYear externalCatalogYear: externalCatalogYearService.getAll()) {
            catalogYears.put(externalCatalogYear.getCode(), externalCatalogYear);
        }
        return catalogYears;
    }

	@Override
	protected boolean overridesCsvRendering() {
		return true;
	}

	@Override
	public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<MapTransferGoalReportTO> reportResults,
								 String reportViewUrl, String reportType, String reportName,
								 AbstractCsvWriterHelper csvHelper) {
		return new String[] {
				"FIRST_NAME",
				"LAST_NAME",
				"SCHOOL_ID",
				"EMAIL_ADDRESS",
				"PLAN_CREATED_DATE",
				"PLAN_OWNER_FIRST_NAME",
				"PLAN_OWNER_LAST_NAME",
				"TRANSFER_GOAL",
				"PARTIAL",
				"CATALOG_YEAR"
		};
	}

	@Override
	public List<String[]> csvBodyRows(MapTransferGoalReportTO reportResultElement, Map<String, Object> reportParameters,
							   Collection<MapTransferGoalReportTO> reportResults, String reportViewUrl, String reportType, String reportName,
							   AbstractCsvWriterHelper csvHelper) {
		return csvHelper.wrapCsvRowInList(new String[] {
				reportResultElement.getFirstName(),
				reportResultElement.getLastName(),
				reportResultElement.getSchoolId(),
				reportResultElement.getPrimaryEmailAddress(),
				csvHelper.formatDate(reportResultElement.getMapCreatedDate()),
				reportResultElement.getMapOwnerFirstName(),
				reportResultElement.getMapOwnerLastName(),
				reportResultElement.getTransferGoal(),
				reportResultElement.getPartial() ? "true" : "false",
				reportResultElement.getCatalogYearName()
		});
	}
}
