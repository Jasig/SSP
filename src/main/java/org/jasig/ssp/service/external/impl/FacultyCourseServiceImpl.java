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
package org.jasig.ssp.service.external.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.jasig.ssp.dao.external.ExternalFacultyCourseRosterDao;
import org.jasig.ssp.dao.external.FacultyCourseDao;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.external.SearchFacultyCourseTO;
import org.jasig.ssp.transferobject.external.SearchStudentCourseTO;
import org.jasig.ssp.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * FacultyCourse service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class FacultyCourseServiceImpl extends AbstractExternalDataService<FacultyCourse> implements
        FacultyCourseService {

	@Autowired
	transient private FacultyCourseDao dao;

	@Autowired
	transient private ExternalFacultyCourseRosterDao externalFacultyCourseRosterDao;

	@Autowired
	private transient TermService termService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyCourseServiceImpl.class);

    @Override
	protected FacultyCourseDao getDao() {
		return dao;
	}

	protected void setDao(final FacultyCourseDao dao) {
		this.dao = dao;
	}


	@Override
    public List<FacultyCourse> getAllCoursesForFaculty(final String facultySchoolId) throws ObjectNotFoundException {
        return dao.getAllCoursesForFaculty(facultySchoolId);
    }

	@Override
	public List<FacultyCourse> getAllCoursesForFacultySortedByTerm(final String facultySchoolId)
            throws ObjectNotFoundException {

        final Map<String, List<FacultyCourse>> facultyCoursesByTermCode = Maps.newHashMap();
        final List<FacultyCourse> rawFacultyCoursesList = dao.getAllCoursesForFaculty(facultySchoolId);

        if (CollectionUtils.isEmpty(rawFacultyCoursesList)) {
            return rawFacultyCoursesList; //faculty has no classes
        }

        //Iterate through FacultyCourses and record distinct term codes and put courses in bucket based on termCode
        for (FacultyCourse course : rawFacultyCoursesList) {
            if (facultyCoursesByTermCode.containsKey(course.getTermCode())) {
                facultyCoursesByTermCode.get(course.getTermCode()).add(course);
            } else {
                facultyCoursesByTermCode.put(course.getTermCode(), Lists.newArrayList(course));
            }
        }

        final List<Term> terms = termService.getTermsByCodes(Lists.newArrayList(facultyCoursesByTermCode.keySet()));

        if (CollectionUtils.isEmpty(terms)) {
            return rawFacultyCoursesList; //no terms or facultyCourse.termCodes don't match externalTerm; can't sort
        }

        //Sort terms descending based on startDate
        Collections.sort(terms, new Comparator<Term>(){
            public int compare(Term o1, Term o2){
                if (DateTimeUtils.midnightOn(o1.getStartDate()) == DateTimeUtils.midnightOn(o2.getStartDate())) {
                    return 0;
                }
                return o1.getStartDate().after(o2.getStartDate()) ? -1 : 1;
            }
        });

        final Term currentTerm = termService.getCurrentTerm();
        final List<FacultyCourse> sortedFacultyCourses = Lists.newArrayListWithCapacity(rawFacultyCoursesList.size());

        //Iterate through terms put currentTerm facultyCourse(s) at top rest descending
        for (Term term : terms) {
            if ( sortedFacultyCourses.isEmpty() ||
                    (currentTerm != null && term.getCode().trim().equals(currentTerm.getCode().trim())) ) {
                sortedFacultyCourses.addAll(0, facultyCoursesByTermCode.get(term.getCode())); //either first record or current term; add to beginning
            } else if (!sortedFacultyCourses.isEmpty()) {
                sortedFacultyCourses.addAll(facultyCoursesByTermCode.get(term.getCode()));
            } else {
                //do nothing
            }
            facultyCoursesByTermCode.remove(term.getCode());
        }

        //term codes in faculty courses may not exist in external_term if so add them at the end)
        if (!facultyCoursesByTermCode.isEmpty()) {
            for (List<FacultyCourse> facultyCoursesForTerm : facultyCoursesByTermCode.values()) {
                sortedFacultyCourses.addAll(facultyCoursesForTerm);
            }
            LOGGER.info("Faculty courses for {}, contains term_codes that aren't in external_term!", facultySchoolId);
        }

        if (sortedFacultyCourses.size() != rawFacultyCoursesList.size()) {
            LOGGER.info("Problem in sorting Faculty Courses for {} expected results don't match! ", facultySchoolId);
            return rawFacultyCoursesList;
        } else {
            return sortedFacultyCourses;
        }
	}

	@Override
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourse(
	        final String facultySchoolId, final String formattedCourse) throws ObjectNotFoundException {

        return externalFacultyCourseRosterDao.getRosterByFacultySchoolIdAndCourse(facultySchoolId, formattedCourse);
	}

	@Override
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourseAndTermCode(
			String facultySchoolId, String formattedCourse, String termCode) throws ObjectNotFoundException {

        return externalFacultyCourseRosterDao.getRosterByFacultySchoolIdAndCourseAndTermCode(
				facultySchoolId, formattedCourse, termCode);
	}
	
	@Override
	public List<ExternalFacultyCourseRoster> getFacultyCourseRoster(SearchFacultyCourseTO searchFacultyCourse)
	    throws ObjectNotFoundException{

	    return externalFacultyCourseRosterDao.getFacultyCourseRoster(searchFacultyCourse);
	}

	@Override
	public ExternalFacultyCourseRoster getEnrollment(SearchStudentCourseTO searchStudentCourse)
            throws ObjectNotFoundException {

	    return externalFacultyCourseRosterDao.getEnrollment(searchStudentCourse);
	}

	@Override
	public ExternalFacultyCourseRoster getEnrollment(String facultySchoolId, String formattedCourse, String termCode,
                                                     String studentSchoolId) throws ObjectNotFoundException {

	    return externalFacultyCourseRosterDao.getEnrollment(facultySchoolId, formattedCourse, termCode, studentSchoolId);
	}

	@Override
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(String facultySchoolId, String formattedCourse)
			throws ObjectNotFoundException {

		return dao.getCourseByFacultySchoolIdAndFormattedCourse(facultySchoolId, formattedCourse);
	}

	@Override
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
	        String facultySchoolId, String formattedCourse, String termCode) throws ObjectNotFoundException {

        return dao.getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(facultySchoolId, formattedCourse, termCode);
	}
	
	@Override
	public FacultyCourse getCourseBySearchFacultyCourseTO(SearchFacultyCourseTO searchFacultyCourse)
			throws ObjectNotFoundException {

	    return dao.getCourseBySearchFacultyCourseTO(searchFacultyCourse);
	}
}