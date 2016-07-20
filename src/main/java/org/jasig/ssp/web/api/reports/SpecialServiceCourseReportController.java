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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PersonSpecialServiceGroupService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.SpecialServiceStudentCoursesTO;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
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
 * Special Service Student Course Report Controller
 *  Produces a report to identify students, special service groups
 *   and course status and grade information.
 * <p>
 * Mapped to URI path <code>/1/report/specialservicestudentcourses</code>
 */
@Controller
@RequestMapping("/1/report/specialservicestudentcourses")
public class SpecialServiceCourseReportController extends ReportBaseController<SpecialServiceStudentCoursesTO> {

	private static final String REPORT_URL = "/reports/specialServiceCourseReport.jasper";
	private static final String REPORT_FILE_TITLE = "Special_Service_Course_Report";
	private static final String REPORT_TITLE = "Special Service Student Course Report";
    private static final String REPORT_DATE_FORMAT="MMM-dd-yyyy hh:mmaaa";
    private static final String NOT_USED = "Not Used";

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialServiceCourseReportController.class);

    @Autowired
    private transient TermService termService;

    @Autowired
    private transient CampusService campusService;

    @Autowired
    private transient SpecialServiceGroupService specialServiceGroupService;

    @Autowired
    private transient StudentTypeService studentTypeService;

    @Autowired
    private transient ServiceReasonService serviceReasonService;

    @Autowired
    private transient PersonService personService;

    @Autowired
    private transient PersonSpecialServiceGroupService personSpecialServiceGroupService;

    @Autowired
    private transient ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;


    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize(Permission.SECURITY_REPORT_READ)
    public @ResponseBody
    void getStudentSpecialServiceCourseInformation(
            final HttpServletResponse response,
            final @RequestParam(required = false) List<UUID> serviceReasonIds,
            final @RequestParam(required = false) List<UUID> studentTypeIds,
            final @RequestParam(required = false) List<UUID> specialServiceGroupIds,
            final @RequestParam(required = false) List<UUID> homeCampusIds,
            final @RequestParam(required = false) String specialServiceCourseStatuses,
            final @RequestParam(required = false) String specialServiceCourseGrades,
            final @RequestParam(required = false) String termCode,
            final @RequestParam(required = false) Date createDateFrom,
            final @RequestParam(required = false) Date createDateTo,
            final @RequestParam(required = false) ObjectStatus objectStatus,
            final @RequestParam(required = false, defaultValue = DEFAULT_REPORT_TYPE) String reportType)
            throws ObjectNotFoundException, IOException {

        final Map<String, Object> parameters = Maps.newHashMap();
        List<String> termCodes = Lists.newArrayList();
        List<String> grades = Lists.newArrayList();
        List<String> statusCodes = Lists.newArrayList();
        List<SpecialServiceGroup> ssgList = Lists.newArrayList();

        handleReportParameters(specialServiceCourseStatuses, specialServiceCourseGrades, termCode,
                createDateFrom, createDateTo, specialServiceGroupIds, homeCampusIds, parameters,
                statusCodes, grades, termCodes, ssgList);

        final Map<String, Pair<String, List<String>>> studentsWithSSGs = personSpecialServiceGroupService.
                getAllSSGNamesWithCampusForInternalAndExternalStudentsWithSSGs(ssgList);

        final List<SpecialServiceStudentCoursesTO> results = externalStudentTranscriptCourseService.
                getTranscriptCoursesBySchoolIds(Lists.newArrayList(studentsWithSSGs.keySet()), termCodes,
                        grades, statusCodes);

        final Map<String, String> facultyNameBySchoolId = Maps.newHashMap();
        for (SpecialServiceStudentCoursesTO course : results) {

            final Pair<String, List<String>> ssgNamesWithCampus = studentsWithSSGs.get(course.getSchoolId());
            course.setCampusName(ssgNamesWithCampus.getFirst());
            course.setSpecialServiceGroupNames(ssgNamesWithCampus.getSecond());

            if (facultyNameBySchoolId.containsKey(course.getFacultySchoolId())) {
                course.setFacultyName(facultyNameBySchoolId.get(course.getFacultySchoolId()));
            } else {
                try {
                    final Person instructor = personService.getInternalOrExternalPersonBySchoolIdLite(
                            course.getFacultySchoolId());
                    course.setFacultyName(instructor.getFullName());
                    facultyNameBySchoolId.put(course.getFacultySchoolId(), instructor.getFullName());

                } catch (ObjectNotFoundException ofne) {
                    //Skip exception here already logged
                }
            }
        }

        renderReport(response,  parameters, results, REPORT_URL, reportType, REPORT_FILE_TITLE);
    }

    @Override
    protected boolean overridesCsvRendering() {
        return true;
    }

    @Override
    public String[] csvHeaderRow(Map<String, Object> reportParameters,
                                 Collection<SpecialServiceStudentCoursesTO> reportResults,
                                 String reportViewUrl, String reportType, String reportName,
                                 AbstractCsvWriterHelper csvHelper) {
        return new String[] {
                "CAMPUS",
                "STUDENT_ID",
                "STUDENT_FULL_NAME",
                "SPECIAL_SERVICE_GROUP",
                "TERM",
                "COURSE",
                "COURSE TITLE",
                "INSTRUCTOR",
                "STATUS_CODE",
                "GRADE"
        };
    }

    @Override
    public List<String[]> csvBodyRows(SpecialServiceStudentCoursesTO reportResultElement, Map<String, Object> reportParameters,
                                      Collection<SpecialServiceStudentCoursesTO> reportResults, String reportViewUrl,
                                      String reportType, String reportName, AbstractCsvWriterHelper csvHelper) {
        return csvHelper.wrapCsvRowInList(new String[] {
                reportResultElement.getCampusName(),
                reportResultElement.getSchoolId(),
                reportResultElement.getStudentName(),
                reportResultElement.getSpecialServiceGroupNamesForDisplay(),
                reportResultElement.getTermCode(),
                reportResultElement.getFormattedCourse(),
                reportResultElement.getCourseTitle(),
                reportResultElement.getFacultyName(),
                reportResultElement.getStatusCode(),
                reportResultElement.getGrade()
        });
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }


    private void handleReportParameters(final String specialServiceCourseStatuses,
                                        final String specialServiceCourseGrades,
                                        final String termCode, final Date createDateFrom, final Date createDateTo,
                                        final List<UUID> specialServiceGroupIds, final List<UUID> homeCampusIds,
                                        final Map<String, Object> parameters,
                                        List<String> statusCodes, List<String> grades, List<String> termCodes,
                                        List<SpecialServiceGroup> ssgList)
            throws ObjectNotFoundException {

        parameters.put("reportTitle", REPORT_TITLE);
        parameters.put("reportDate", new SimpleDateFormat(REPORT_DATE_FORMAT).format(new Date()));

        //Course Status Codes
        if (StringUtils.isNotBlank(specialServiceCourseStatuses)) {
            statusCodes.addAll(Lists.newArrayList(specialServiceCourseStatuses.trim().split(",")));
            parameters.put("courseStatusCodes", specialServiceCourseStatuses.trim());
        } else {
            parameters.put("courseStatusCodes", NOT_USED);
        }

        //Course Grades
        if (StringUtils.isNotBlank(specialServiceCourseGrades)) {
            grades.addAll(Lists.newArrayList(specialServiceCourseGrades.trim().split(",")));
            parameters.put("courseGrades", specialServiceCourseGrades.trim());
        } else {
            parameters.put("courseGrades", NOT_USED);
        }

        //Term Names or Date to and From
        if (StringUtils.isNotBlank(termCode) && createDateFrom == null && createDateTo == null) {
            final Term term = termService.getByCode(termCode);
            if (term != null) {
                termCodes.add(term.getCode());
                parameters.put("termName", term.getName());
            }
            parameters.put("termCode", termCode);
            parameters.put("startDate", NOT_USED);
            parameters.put("endDate", NOT_USED);

        } else if (createDateFrom != null && createDateTo != null) {
            final List<String> terms = termService.getTermCodesByDateRange(createDateFrom, createDateTo);
            final SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            termCodes.addAll(terms);

            parameters.put("startDate", sdf.format(createDateFrom));
            parameters.put("endDate", sdf.format(createDateTo));
            parameters.put("termCode", NOT_USED);
        } else {
            parameters.put("startDate", NOT_USED);
            parameters.put("endDate", NOT_USED);
            parameters.put("termCode", NOT_USED);
        }

        //Special Service Group Names
        if (CollectionUtils.isEmpty(specialServiceGroupIds)) {
            ssgList.addAll(Lists.newArrayList(specialServiceGroupService.getAll(null)));
        } else {
            ssgList.addAll(specialServiceGroupService.get(specialServiceGroupIds));
        }

        final StringBuilder ssgNames = new StringBuilder();
        for (SpecialServiceGroup ssg : ssgList) {
            if (ssgNames.length() > 0) {
                ssgNames.append(ssg.getName() + ", ");
            } else {
                ssgNames.append(ssg.getName());
            }
        }
        parameters.put("specialServiceGroupNames", ssgNames.toString());

        //Home Campus Names
        if (CollectionUtils.isNotEmpty(homeCampusIds)) {
            final List<Campus> campusList = campusService.get(homeCampusIds);

            final StringBuilder campusNames = new StringBuilder();
            for (Campus campus : campusList) {
                if (campusNames.length() > 0) {
                    campusNames.append(campus.getName() + ", ");
                } else {
                    campusNames.append(campus.getName());
                }
            }
            parameters.put("campusNames", campusNames.toString());
        } else {
            parameters.put("campusNames", NOT_USED);
        }
    }
}