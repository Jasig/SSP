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


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.transferobject.reports.EarlyAlertReasonCountsTO;
import org.jasig.ssp.util.DateTerm;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.collections.Triple;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


/**
 * Service methods for Reporting on Early Alert Reasons per Student and Counts
 * <p>
 * Mapped to URI path <code>/1/report/earlyalertreaasoncounts</code>
 */
@Controller
@RequestMapping("/1/report/earlyalertreasoncounts")
public class EarlyAlertReasonCountsReportController extends ReportBaseController<EarlyAlertReasonCountsTO> {

    private static String REPORT_URL = "/reports/earlyAlertReasonCountsReport.jasper";
    private static String REPORT_FILE_TITLE = "Early_Alert_Reason_Counts_Report";
    private static final String REASON_TOTALS = "reasonTotals";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EarlyAlertReasonCountsReportController.class);

    @Autowired
    protected transient TermService termService;

    @Autowired
    protected transient CampusService campusService;

    @Autowired
    protected transient EarlyAlertService earlyAlertService;

    @Autowired
    protected transient EarlyAlertReasonService earlyAlertReasonService;


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
    void getNumberOfEarlyAlertsByReasons(
            final HttpServletResponse response,
            final @RequestParam(required = false) UUID campusId,
            final @RequestParam(required = false) String termCode,
            final @RequestParam(required = false) Date createDateFrom,
            final @RequestParam(required = false) Date createDateTo,
            final @RequestParam(required = false) ObjectStatus objectStatus,
            final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
            throws ObjectNotFoundException, IOException {

        final DateTerm dateTerm = new DateTerm(createDateFrom, createDateTo, termCode, termService);
        final Map<String, Object> parameters = Maps.newHashMap();
        final Campus campus = SearchParameters.getCampus(campusId, campusService);

        if ( StringUtils.isBlank(termCode) || termCode.trim().toLowerCase().equals("not used") && createDateFrom != null ) {
            dateTerm.setTerm(null);
        } else if (termCode != null && createDateFrom == null) {
            dateTerm.setStartEndDates(null, null);
        }

        SearchParameters.addCampusToParameters(campus, parameters);
        SearchParameters.addDateTermToMap(dateTerm, parameters);

        List<Triple<String, Long, Long>> reasonTotals = earlyAlertService.getEarlyAlertReasonTypeCountByCriteria(campus, dateTerm.getTermCodeNullPossible(), dateTerm.getStartDate(), dateTerm.getEndDate(), objectStatus);

        List<EarlyAlertReasonCountsTO> results = earlyAlertService.getStudentEarlyAlertReasonCountByCriteria(dateTerm.getTermCodeNullPossible(), dateTerm.getStartDate(), dateTerm.getEndDate(), campus, objectStatus);

        if ( results == null) {
            results = new ArrayList<>();
        }

        SearchParameters.addDateTermToMap(dateTerm, parameters);
        parameters.put(REASON_TOTALS, reasonTotals);

        renderReport( response,  parameters, results, REPORT_URL, reportType, REPORT_FILE_TITLE);
    }

    @Override
    protected boolean overridesCsvRendering() {
        return true;
    }

    @Override
    public String[] csvHeaderRow(Map<String, Object> reportParameters, Collection<EarlyAlertReasonCountsTO> reportResults,
                                 String reportViewUrl, String reportType, String reportName,
                                 AbstractCsvWriterHelper csvHelper) {
        return new String[] {
                "STUDENT ID",
                "LAST",
                "FIRST",
                "COURSE REPORTED",
                "INSTRUCTOR",
                "TOTAL REFERRAL REASONS"
        };
    }

    @Override
    public List<String[]> csvBodyRows(EarlyAlertReasonCountsTO reportResultElement, Map<String, Object> reportParameters,
                                      Collection<EarlyAlertReasonCountsTO> reportResults, String reportViewUrl, String reportType, String reportName,
                                      AbstractCsvWriterHelper csvHelper) {
        return csvHelper.wrapCsvRowInList(new String[] {
                reportResultElement.getSchoolId(),
                reportResultElement.getLastName(),
                reportResultElement.getFirstName(),
                reportResultElement.getCourseName(),
                reportResultElement.getFacultyName(),
                csvHelper.formatLong(reportResultElement.getTotalReasonsReported())
        });
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}