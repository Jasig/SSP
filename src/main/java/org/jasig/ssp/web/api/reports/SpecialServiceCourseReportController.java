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
import org.jasig.ssp.model.*;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalStudentSpecialServiceGroupService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.reports.SpecialServiceStudentCoursesTO;
import org.jasig.ssp.util.csvwriter.AbstractCsvWriterHelper;
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
    private transient PersonSearchService personSearchService;

    @Autowired
    private transient ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;

    @Autowired
    private transient ExternalStudentSpecialServiceGroupService externalStudentSpecialServiceGroupService;


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
        final List<String> termCodes = Lists.newArrayList();
        final List<String> grades = Lists.newArrayList();
        final List<String> statusCodes = Lists.newArrayList();
        final List<SpecialServiceGroup> ssgList = Lists.newArrayList();
        final PersonSearchRequest reqForm = new PersonSearchRequest();
        final SortingAndPaging sAndP = new SortingAndPaging(null, null, null, null, null, null);
        final Map<String, String> facultyNameBySchoolId = Maps.newHashMap();
        final Map<String, PersonSearchResultFull> studentResultMap = Maps.newHashMap();

        reqForm.setSortAndPage(sAndP);
        handleReportParametersAndSearchForm(reqForm, specialServiceCourseStatuses, specialServiceCourseGrades, termCode,
                createDateFrom, createDateTo, specialServiceGroupIds, homeCampusIds, parameters,
                statusCodes, grades, termCodes, ssgList);

        handleRetrievalOfStudents(reqForm, studentResultMap);

        final List<SpecialServiceStudentCoursesTO> courseResults = externalStudentTranscriptCourseService.
                getTranscriptCoursesBySchoolIds(Lists.newArrayList(studentResultMap.keySet()), termCodes,
                        grades, statusCodes);

        for (SpecialServiceStudentCoursesTO course : courseResults) {

            //load ssgs and campusName for student into report TO
            final PersonSearchResultFull student = studentResultMap.get(course.getSchoolId());
            course.setCampusName(student.getCampusName());
            course.setSpecialServiceGroupNamesForDisplay(student.getSpecialServiceGroups());

            //load course facultyName for student in report TO
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

        renderReport(response,  parameters, courseResults, REPORT_URL, reportType, REPORT_FILE_TITLE);
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


    private void handleReportParametersAndSearchForm(final PersonSearchRequest psr,
                                        final String specialServiceCourseStatuses,
                                        final String specialServiceCourseGrades,
                                        final String termCode, final Date createDateFrom, final Date createDateTo,
                                        final List<UUID> specialServiceGroupIds, final List<UUID> homeCampusIds,
                                        final Map<String, Object> parameters,
                                        List<String> statusCodes, List<String> grades, List<String> termCodes,
                                        List<SpecialServiceGroup> ssgList) throws ObjectNotFoundException {

        parameters.put("reportTitle", REPORT_TITLE);
        parameters.put("reportDate", new SimpleDateFormat(REPORT_DATE_FORMAT).format(new Date()));

        //Course Status Codes
        if (StringUtils.isNotBlank(specialServiceCourseStatuses)) {
            statusCodes.addAll(Lists.newArrayList(specialServiceCourseStatuses.trim().split("\\s*,\\s*")));
            parameters.put("courseStatusCodes", specialServiceCourseStatuses.trim());
        } else {
            parameters.put("courseStatusCodes", NOT_USED);
        }

        //Course Grades
        if (StringUtils.isNotBlank(specialServiceCourseGrades)) {
            grades.addAll(Lists.newArrayList(specialServiceCourseGrades.trim().split("\\s*,\\s*")));
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
            final StringBuilder termNames = new StringBuilder();

            termCodes.addAll(terms);

            for (String term : terms) {
                if (termNames.length() > 0) {
                  termNames.append(", ");
                }
                termNames.append(term);
            }

            parameters.put("termName", termNames.toString());
            parameters.put("startDate", sdf.format(createDateFrom));
            parameters.put("endDate", sdf.format(createDateTo));

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
                ssgNames.append(", ");
            }
            ssgNames.append(ssg.getName());
        }
        parameters.put("specialServiceGroupNames", ssgNames.toString());

        psr.setSpecialServiceGroup(ssgList);

        //Home Campus Names
        if (CollectionUtils.isNotEmpty(homeCampusIds)) {
            final List<Campus> campusList = campusService.get(homeCampusIds);

            final StringBuilder campusNames = new StringBuilder();
            for (Campus campus : campusList) {
                if (campusNames.length() > 0) {
                    campusNames.append(", ");
                }
                campusNames.append(campus.getName());
            }

            parameters.put("campusNames", campusNames.toString());
            psr.setHomeCampus(campusList);
        } else {
            parameters.put("campusNames", NOT_USED);
        }
    }

    private void handleRetrievalOfStudents(final PersonSearchRequest psr,
                                           final Map<String, PersonSearchResultFull> studentMap) {
        //get students by ssg (internal/external)
        final List<PersonSearchResultFull> students = Lists.newArrayList(
                personSearchService.searchPersonDirectoryFull(psr));

        //get list of external students (they won't have ssgs populated)
        final List<String> externalOnlySchoolIds = Lists.newArrayList();
        final List<UUID> internalPersonUUIDs = Lists.newArrayList();
        for (PersonSearchResultFull person : students) {
            if (person.getId() == null) {
                externalOnlySchoolIds.add(person.getSchoolId());
            } else {
                internalPersonUUIDs.add(person.getId());
            }

            studentMap.put(person.getSchoolId(), person);
        }

        //get external only ssgs and load into result map
        final Map<String, Set<SpecialServiceGroup>> externalOnlySSGsBySchoolId =
                externalStudentSpecialServiceGroupService.getMultipleStudentsExternalSSGsSyncedAsInternalSSGs(
                        externalOnlySchoolIds);
        for (String schoolId : externalOnlySSGsBySchoolId.keySet()) {
            studentMap.get(schoolId).setSpecialServiceGroups(externalOnlySSGsBySchoolId.get(schoolId));
        }

        //get internal only ssgs and load into result map
        final List<Person> persons = personService.peopleFromListOfIds(internalPersonUUIDs, null);
        for (Person personIterator : persons) {
            studentMap.get(personIterator.getSchoolId()).setSpecialServiceGroupsFromPersonSSGs(
                    personIterator.getSpecialServiceGroups());
        }

    }
}