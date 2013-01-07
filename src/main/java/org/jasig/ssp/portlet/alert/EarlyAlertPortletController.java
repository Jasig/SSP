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

import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.Person;
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

import javax.portlet.PortletRequest;
import java.util.Map;

@Controller
@RequestMapping("VIEW")
public final class EarlyAlertPortletController {
	
	private static final String KEY_STUDENT_ID = "studentId";
	private static final String KEY_COURSE = "course";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersonService personService;
	
	@Autowired
	private FacultyCourseService facultyCourseService;

	@RenderMapping
	public String showRoster(final PortletRequest req) {
		return "ea-roster";
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
			/*
			 * NB:  It's on us to translate from schoolId <-> studentId (SSP 
			 * UUID) at this point in the Early Alert process.  Previous APIs 
			 * user the former where following APIs use the later.
			 */
			student = personService.getBySchoolId(schoolId);  // TODO:  Handle error better??
		} catch (ObjectNotFoundException e) {
			throw new RuntimeException("Unrecognized entity", e);
		}
		/*
		 *  SANITY CHECK (is this even necessary?  wanted?)
		 *    - Confirm that the logged in user is the faculty of record on the 
		 *      course
		 */
		if (!course.getFacultySchoolId().equals(user.getSchoolId())) {
			final String msg = "Logged in user must be faculty of record on " +
													"the specified course.";
			throw new IllegalStateException(msg);
		}
		model.put(KEY_STUDENT_ID, student.getId());  // Student UUID
		model.put(KEY_COURSE, course);
		return new ModelAndView("ea-form", model);
	}

	@RenderMapping(params = "confirm=true")
	public ModelAndView confirm(@RequestParam final String studentName) {
		return new ModelAndView("ea-roster", "studentName", studentName);
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
}