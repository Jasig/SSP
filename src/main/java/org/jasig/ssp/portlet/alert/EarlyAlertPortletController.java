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
package org.jasig.ssp.portlet.alert;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public final class EarlyAlertPortletController {
	
	private static final String KEY_STUDENT_ID = "studentId";
	private static final String KEY_COURSE = "course";
	private static final String KEY_ENROLLMENT = "enrollment";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersonService personService;
	
	@Autowired
	private FacultyCourseService facultyCourseService;

	@RenderMapping
	public ModelAndView showRoster(final PortletRequest req) {
		String formattedCourse = req.getParameter("formattedCourse");
		String initialSelectedCourse = "";
		String termCode = req.getParameter("termCode");
		if(org.apache.commons.lang.StringUtils.isNotBlank(formattedCourse))
			initialSelectedCourse = formattedCourse;
		if(org.apache.commons.lang.StringUtils.isNotBlank(termCode))
			initialSelectedCourse += ":" + termCode;
		return new ModelAndView("ea-roster", "initialSelectedCourse", initialSelectedCourse);
	}

	@RenderMapping(params = "action=enterAlert")
	public ModelAndView showForm(final PortletRequest req, 
			@RequestParam final String schoolId, 
			@RequestParam final String formattedCourse,
			@RequestParam(required = false) final String termCode,
			ModelMap model) {
		// Do not use a @ModelAttribute-annotated argument to get the user
		// out of the model b/c Spring will attempt to set properties on it
		// by matching up request param names. This will overwrite user.schoolId
		// with the method param of that name, effectively copying the student's
		// school ID into the faculty user's record.
		Person user = (Person)model.get("user");
		if ( user == null ) {
			throw new RuntimeException("Missing or deactivated account for remote user "
					+ req.getRemoteUser());
		}
		FacultyCourse course = null;
		Person student = null;
		ExternalFacultyCourseRoster enrollment = null;
		try {
			// Should really always have a term code (see deprecation notes for
			// getCourseByFacultySchoolIdAndFormattedCourse) but we know at
			// least one real-world deployment (SPC) cannot/does not send term
			// codes when deep linking to the EA form *and* this just happens to
			// work b/c their formattedCourse values are globally unique. So
			// we preserve the option of not filtering by term code.
			if ( !(StringUtils.hasText(termCode)) ) {
				course = facultyCourseService.getCourseByFacultySchoolIdAndFormattedCourse(
						user.getSchoolId(), formattedCourse);
			} else {
				course = facultyCourseService.getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
						user.getSchoolId(), formattedCourse, termCode);
			}

			if ( course == null ) {
				throw new IllegalStateException(buildErrorMesssage("Course not found for current instructor:", user.getSchoolId(), null, formattedCourse, termCode));
			}

			/*
			 * NB:  It's on us to translate from schoolId <-> studentId (SSP 
			 * UUID) at this point in the Early Alert process.  Previous APIs 
			 * user the former where following APIs use the later.
			 */
			student = personService.getBySchoolId(schoolId,true);  // TODO:  Handle error better??

			if ( student == null ) {
				throw new IllegalStateException("Student not found: " + schoolId);
			}
			
			enrollment = facultyCourseService.getEnrollment(user.getSchoolId(),
					formattedCourse, course.getTermCode(), schoolId);

			if ( enrollment == null ) {
				throw new IllegalStateException(buildErrorMesssage("Enrollment not found for: ", 
						user.getSchoolId(), 
						schoolId, 
						formattedCourse, 
						termCode));
			}

		} catch (ObjectNotFoundException e) {
			throw new RuntimeException(buildErrorMesssage("Unrecognized Entity: ", 
					user.getSchoolId(), 
					schoolId, 
					formattedCourse, 
					termCode), e);
		}
		/*
		 *  SANITY CHECK (is this even necessary?  wanted?)
		 *    - Confirm that the logged in user is the faculty of record on the 
		 *      course
		 */
		if (!course.getFacultySchoolId().equals(user.getSchoolId())) {
			throw new IllegalStateException(buildErrorMesssage("Logged in user must be faculty of record on the specified course: ", 
					user.getSchoolId(), 
					null, 
					formattedCourse, 
					termCode));
		}
		model.put(KEY_STUDENT_ID, student.getId());  // Student UUID
		model.put(KEY_COURSE, course);
		model.put(KEY_ENROLLMENT, enrollment);
		return new ModelAndView("ea-form", model);
	}

	@RenderMapping(params = "confirm=true")
	public ModelAndView confirm(final PortletRequest req, @RequestParam final String studentName) {
		String formattedCourse = req.getParameter("formattedCourse");
		String initialSelectedCourse = "";
		String termCode = req.getParameter("termCode");
		if(org.apache.commons.lang.StringUtils.isNotBlank(formattedCourse))
			initialSelectedCourse = formattedCourse;
		if(org.apache.commons.lang.StringUtils.isNotBlank(termCode))
			initialSelectedCourse += ":" + termCode;
		Map<String,String> model = new HashMap<String,String>();
		model.put("studentName", org.apache.commons.lang.StringEscapeUtils.unescapeJavaScript(studentName));
		model.put("initialSelectedCourse", initialSelectedCourse);
		return new ModelAndView("ea-roster", model);
	}
	
	@ModelAttribute("user")
	public Person getUser(final PortletRequest req) {
		Person rslt = null;
		try {
			rslt = findOrCreateEnabledPersonForCurrentPortletUser(req);
		} catch (UnableToCreateAccountException e) {
			log.error("User is new to SSP, but account creation failed.", e);
		} catch (UserNotEnabledException e) {
			log.error("User is present in SSP, but disabled.", e);
		}
		// Keeping with 'legacy' behavior, we'll return null if the above
		// errored in some kind of recognizable way... the JSP will have to act
		// appropriately
		return rslt;
	}

	private Person findOrCreateEnabledPersonForCurrentPortletUser(final PortletRequest req)
	throws UserNotEnabledException, UnableToCreateAccountException {
		Person person = null;
		@SuppressWarnings("unchecked") Map<String,String> userInfo =
				(Map<String,String>) req.getAttribute(PortletRequest.USER_INFO);
		String username = null;
		if ( userInfo != null ) {
			username = userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID.toString());
		}
		username = StringUtils.hasText(username) ? username : req.getRemoteUser();
		if ( !(StringUtils.hasText(username)) ) {
			throw new IllegalArgumentException(
					"Cannot lookup nor create an account without a username");
		}

		try {
			person = findEnabledPersonByUsernameOrFail(username);
		} catch ( ObjectNotFoundException e ) {
			try {
				return personService.createUserAccountForCurrentPortletUser(username, req);
			} catch ( ObjectExistsException ee ) {
				try {
					person = findEnabledPersonByUsernameOrFail(username);
				} catch ( ObjectNotFoundException eee ) {
					throw new UnableToCreateAccountException(
							"Couldn't create account with username" + username
							+ " because an account with that username seemed"
							+ " to already exist, but was unable to load that"
							+ " existing account.", eee);
				} // UserNotEnabledException is helpfully descriptive so just
				  // let it bubble up
			}
		}
		return person;
	}

	private Person findEnabledPersonByUsernameOrFail(String username)
	throws ObjectNotFoundException, UserNotEnabledException {
		Person person = personService.personFromUsername(username);
		if (person.isDisabled()) {
			throw new UserNotEnabledException("User '" + username + "' is disabled.");
		}
		return person;
	}
	
	private String buildErrorMesssage(String prefix, String facultySchoolId, String studentSchoolId, String formattedCourse, String termCode){
		StringBuilder errorMsg = new StringBuilder(prefix);
		if(org.apache.commons.lang.StringUtils.isNotBlank(facultySchoolId))
			errorMsg.append(" Faculty School ID:").append(facultySchoolId);
		if(org.apache.commons.lang.StringUtils.isNotBlank(studentSchoolId))
			errorMsg.append(" Student School ID:").append(studentSchoolId);
		if(org.apache.commons.lang.StringUtils.isNotBlank(formattedCourse))
			errorMsg.append(" Formatted Course:").append(formattedCourse);
		if(org.apache.commons.lang.StringUtils.isNotBlank(termCode))
			errorMsg.append(" TermCode:").append(termCode);
		errorMsg.append("\n\n");
		return errorMsg.toString();
	}
}