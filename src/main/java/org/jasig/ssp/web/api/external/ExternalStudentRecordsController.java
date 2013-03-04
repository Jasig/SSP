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
package org.jasig.ssp.web.api.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.factory.external.ExternalStudentAcademicProgramTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentRecordsLiteTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentRecordsTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentTestTOFactory;
import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.model.external.ExternalStudentTermCourses;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalStudentAcademicProgramService;
import org.jasig.ssp.service.external.ExternalStudentTestService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsLiteTO;
import org.jasig.ssp.transferobject.external.ExternalStudentRecordsTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTermCoursesTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTestTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.tool.IntakeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import java.util.List;

@Controller
@RequestMapping("/1/person/{id}")
public class ExternalStudentRecordsController extends AbstractBaseController {

	
	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient ExternalStudentTranscriptService externalStudentTranscriptService;
	
	@Autowired
	private transient ExternalStudentAcademicProgramService externalStudentAcademicProgramService;
	
	@Autowired
	private transient ExternalStudentAcademicProgramTOFactory programFactory;
		
	@Autowired
	private transient ExternalStudentRecordsTOFactory factory;
		
	@Autowired
	private transient  ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;
	
	@Autowired
	private transient  ExternalStudentTranscriptCourseTOFactory externalStudentTranscriptCourseFactory;
	
	@Autowired
	private transient ExternalStudentTestTOFactory externalStudentTestTOFactory;
	
	@Autowired
	private transient ExternalStudentTestService externalStudentTestService;
	

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntakeController.class);
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	/**
	 * Using the Student UUID passed, return the ExternalStudentRecordsLiteTO in its current state,
	 * creating it if necessary.
	 * 
	 * @param id
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/transcript/summary", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentRecordsLiteTO loadSummaryStudentRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		final ExternalStudentRecordsLite record = new ExternalStudentRecordsLite();
		ExternalStudentTranscript transcript = externalStudentTranscriptService.getRecordsBySchoolId(schoolId);
		record.setPrograms(externalStudentAcademicProgramService.getAcademicProgramsBySchoolId(schoolId));
		record.setGPA(transcript);

		ExternalStudentRecordsLiteTO recordTO = new ExternalStudentRecordsLiteTO(record);

		// Have to hydrate the TO ourselves b/c building a collection of TOs
		// in the "correct" way requires access to the corresponding Factory.

		// in general TO constructors can't handle null arguments. multi-valued
		// associations are a special case, though. See below.
		if ( record.getGPA() != null ) {
			recordTO.setGpa(new ExternalStudentTranscriptTO(record.getGPA()));
		}

		// This is standard handling for multi-valued TO associations, which
		// converts null associations to empty lists.
		recordTO.setPrograms(programFactory.asTOList(record.getPrograms()));
		return recordTO;
	}
	
	@RequestMapping(value = "/transcript/full", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	ExternalStudentRecordsTO loadFullStudentRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		ExternalStudentTranscript transcript = externalStudentTranscriptService.getRecordsBySchoolId(schoolId);
		final ExternalStudentRecords record = new ExternalStudentRecords();
		record.setGPA(transcript);
		record.setPrograms(externalStudentAcademicProgramService.getAcademicProgramsBySchoolId(schoolId));
		List<ExternalStudentTranscriptCourse> courses = externalStudentTranscriptCourseService.getTranscriptsBySchoolId(schoolId);
		Map<String, ExternalStudentTermCourses> coursesByTerm = new HashMap<String,ExternalStudentTermCourses>();
		
		for(ExternalStudentTranscriptCourse course:courses){
			if(coursesByTerm.containsKey(course.getTermCode())){
				ExternalStudentTermCourses termCourses = coursesByTerm.get(course.getTermCode());
				termCourses.addCourse(course);
			}else{
				coursesByTerm.put(course.getTermCode(), new ExternalStudentTermCourses(course));
			}
		}
		
		ExternalStudentRecordsTO recordTO = factory.from(record);
		recordTO.setPrograms(programFactory.asTOList(record.getPrograms()));
		List<ExternalStudentTermCoursesTO> coursesTermTOs = new ArrayList<ExternalStudentTermCoursesTO>();
		for(ExternalStudentTermCourses crousesForTerm:coursesByTerm.values())
		{
			ExternalStudentTermCoursesTO to = new ExternalStudentTermCoursesTO(crousesForTerm);
			to.setCourses(externalStudentTranscriptCourseFactory.asTOList(crousesForTerm.getCourses()));
			coursesTermTOs.add(to);
		}
		recordTO.setTerms(coursesTermTOs);
		return recordTO;
	}
	
	/**
	 * Using the Student UUID passed, return the ExternalStudentRecordsLiteTO in its current state,
	 * creating it if necessary.
	 * 
	 * @param id
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalStudentTestTO> loadStudentTestRecords(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		String schoolId = getStudentId(id);
		
		return externalStudentTestTOFactory.asTOList(externalStudentTestService.getStudentTestResults(schoolId));
	}
	
	String getStudentId(UUID id) throws ObjectNotFoundException{
		return personService.get(id).getSchoolId();
	}
	
	

}
