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
import java.util.UUID;

import javax.portlet.PortletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.security.exception.UnableToCreateAccountException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.transferobject.external.SearchFacultyCourseTO;
import org.jasig.ssp.transferobject.external.SearchStudentCourseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public ModelAndView showRoster(final PortletRequest req,
			ModelMap model) {
		
		
		try {
			setInitialSelectedCourse(model, req);
		} catch (ObjectNotFoundException e) {
			//TODO HANDLE FOLLOWING ISSUES
			// 1. Faculty no longer has permissions
			// 2. Faculty not found
			// 3. Course not found
			// 
			throw new EarlyAlertPortletControllerRuntimeException("Missing student identifier.");
		}
		if(isConfirmed(req))
			model.put("studentName", org.apache.commons.lang.StringEscapeUtils.unescapeJavaScript(req.getParameter("studentName")));
			
		return new ModelAndView("ea-roster", model);
	}
	
	private void setInitialSelectedCourse(ModelMap model, final PortletRequest req) throws ObjectNotFoundException{
		String formattedCourse = req.getParameter("formattedCourse");
		String sectionCode = req.getParameter("sectionCode");
		String termCode = req.getParameter("termCode");
		Person user = (Person)model.get("user");
		if ( user == null ) {
			throw new EarlyAlertPortletControllerRuntimeException("Missing or deactivated account for current user.");
		}
		FacultyCourse course = getCourse(model, new SearchFacultyCourseTO( user.getSchoolId(),  
				termCode, 
				sectionCode,  
				formattedCourse));
		if(course != null)
			model.put("initialSelectedCourse", getCourseSectionKey(course));
	}

    private FacultyCourse getCourse(ModelMap model, SearchFacultyCourseTO searchFacultyCourseTO){
    	if(searchFacultyCourseTO.hasCourseIdentification()){
    		FacultyCourse course = null;
    		try{
    			course =  facultyCourseService.getCourseBySearchFacultyCourseTO(searchFacultyCourseTO);
    		} catch(ObjectNotFoundException e){
    			throw new EarlyAlertPortletControllerRuntimeException("Course not found or user not listed as faculty for course. (FormattedCourse:TermCode:SectionCode)" + getCourseSectionKey(searchFacultyCourseTO) );
    		}
    		if(course == null)
    			throw new EarlyAlertPortletControllerRuntimeException("Course not found or user not listed as faculty for course. (FormattedCourse:TermCode:SectionCode)" + getCourseSectionKey(searchFacultyCourseTO) );
    		return course;
    	}
    	return null;
    }
    
    private String getCourseSectionKey(SearchFacultyCourseTO course){
    	return course.getFormattedCourse() + ":" +
    			course.getTermCode() + ":" + course.getSectionCode();
    }
    
    private String getCourseSectionKey(FacultyCourse course){
    	return course.getFormattedCourse() + ":" +
    			course.getTermCode() + ":" + course.getSectionCode();
    }
	
	
	//TODO this is a total hack added to "clear" confirm. portlet does not have access to render parameters so no way to clear
	private Boolean isConfirmed(PortletRequest req){
		
		String confirm = req.getParameter("confirm");
		if(org.apache.commons.lang.StringUtils.isBlank(confirm))
			return false;
		
		String pastConfirm = (String)req.getPortletSession().getAttribute("confirm");
		pastConfirm = org.apache.commons.lang.StringEscapeUtils.unescapeJavaScript(pastConfirm);
		if(org.apache.commons.lang.StringUtils.isBlank(pastConfirm)){
			req.getPortletSession().setAttribute("confirm", confirm);
			return true;
		}
		if(confirm.equals(pastConfirm))
			return false;

		req.getPortletSession().setAttribute("confirm", confirm);
		return true;
	}

	@RenderMapping(params = "action=enterAlert")
	public ModelAndView showForm(final PortletRequest req, 
			@RequestParam(required = false) final String schoolId, 
			@RequestParam(required = false) final String formattedCourse,
			@RequestParam(required = false) final String studentUserName, 
			@RequestParam(required = false) final String sectionCode,
			@RequestParam(required = false) final String termCode,
			ModelMap model) {
		// Do not use a @ModelAttribute-annotated argument to get the user
		// out of the model b/c Spring will attempt to set properties on it
		// by matching up request param names. This will overwrite user.schoolId
		// with the method param of that name, effectively copying the student's
		// school ID into the faculty user's record.
		if(!StringUtils.isNotBlank(schoolId) &&  !StringUtils.isNotBlank(studentUserName)){
			throw new EarlyAlertPortletControllerRuntimeException("Missing student identifier.");
		}
		
		if(!StringUtils.isNotBlank(formattedCourse) &&  !StringUtils.isNotBlank(sectionCode)){
			throw new EarlyAlertPortletControllerRuntimeException("Missing course identifier/s.");
		}
		Person user = (Person)model.get("user");
		if ( user == null ) {
			throw new EarlyAlertPortletControllerRuntimeException("Missing or deactivated account for current user.");
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
			course = facultyCourseService.getCourseBySearchFacultyCourseTO(new SearchFacultyCourseTO( user.getSchoolId(),  
					termCode, 
					sectionCode,  
					formattedCourse));

			if ( course == null ) {
				throw new EarlyAlertPortletControllerRuntimeException(buildErrorMesssage("Course not found or current user is not listed as the instructor of record:",
						user.getSchoolId(),
						schoolId,
						studentUserName,
						formattedCourse,
						termCode,
						sectionCode));
			}

			/*
			 * NB:  It's on us to translate from schoolId <-> studentId (SSP 
			 * UUID) at this point in the Early Alert process.  Previous APIs 
			 * user the former where following APIs use the later.
			 */
			if(StringUtils.isNotBlank(schoolId)) {
				try {
					student = personService.getBySchoolId(schoolId, true);  // TODO:  Handle error better??
					if ( student == null ) {
						throw new EarlyAlertPortletControllerRuntimeException("Student not found by school ID: " + schoolId);
					}
				} catch (ObjectNotFoundException e) {
					throw new EarlyAlertPortletControllerRuntimeException("Student not found by school ID: " + schoolId, e);
				}
			} else {
				try {
					student = personService.getByUsername(studentUserName, true);
					if ( student == null ) {
						throw new EarlyAlertPortletControllerRuntimeException("Student not found by username: " + studentUserName);
					}
				} catch (ObjectNotFoundException e) {
					throw new EarlyAlertPortletControllerRuntimeException("Student not found by username: " + studentUserName, e);
				}

			}

			// Should never happen, but if it is blank, getEnrollment() will effectively give you a random enrollment,
			// which is bad news. Of course.
			if ( StringUtils.isBlank(student.getSchoolId()) ) {
				throw new EarlyAlertPortletControllerRuntimeException("Selected student has no school ID. Username: " + student.getUsername());
			}

			enrollment = facultyCourseService.getEnrollment(new SearchStudentCourseTO(user.getSchoolId(),
					termCode,
					sectionCode,
					formattedCourse,
					student.getSchoolId()));

			if ( enrollment == null ) {
				throw new EarlyAlertPortletControllerRuntimeException(buildErrorMesssage("Enrollment not found for: ",
						user.getSchoolId(),
						student.getSchoolId(),
						student.getUsername(),
						formattedCourse, 
						termCode,
						sectionCode));
			}
		} catch ( EarlyAlertPortletControllerRuntimeException e ) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(buildErrorMesssage("System error looking up course or enrollment for: ",
					user.getSchoolId(),
					student == null ? schoolId : student.getSchoolId(),
					student == null ? studentUserName : student.getUsername(),
					formattedCourse, 
					termCode,
					sectionCode), e);
		}
		/*
		 *  SANITY CHECK (is this even necessary?  wanted?)
		 *    - Confirm that the logged in user is the faculty of record on the 
		 *      course
		 */
		if (!course.getFacultySchoolId().equals(user.getSchoolId())) {
			throw new EarlyAlertPortletControllerRuntimeException(buildErrorMesssage("Current user is not listed as the instructor of record on the specified course: ",
					user.getSchoolId(),
					student.getSchoolId(),
					student.getUsername(),
					formattedCourse, 
					termCode,
                    sectionCode));
		}
		model.put(KEY_STUDENT_ID, student.getId());  // Student UUID
		model.put(KEY_COURSE, course);
		model.put(KEY_ENROLLMENT, enrollment);
		return new ModelAndView("ea-form", model);
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
		username = StringUtils.isNotBlank(username) ? username : req.getRemoteUser();
		if ( !(StringUtils.isNotBlank(username)) ) {
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
	
	private String buildErrorMesssage(String prefix, String facultySchoolId, String studentSchoolId, String studentUsername, String formattedCourse, String termCode, String sectionCode){
		StringBuilder errorMsg = new StringBuilder(prefix);
		if(org.apache.commons.lang.StringUtils.isNotBlank(facultySchoolId))
			errorMsg.append(" [Faculty School ID: ").append(facultySchoolId).append("]");
		if(org.apache.commons.lang.StringUtils.isNotBlank(studentSchoolId))
			errorMsg.append(" [Student School ID: ").append(studentSchoolId).append("]");
		if(org.apache.commons.lang.StringUtils.isNotBlank(studentUsername))
			errorMsg.append(" [Student Username: ").append(studentUsername).append("]");
		if(org.apache.commons.lang.StringUtils.isNotBlank(formattedCourse))
			errorMsg.append(" [Formatted Course: ").append(formattedCourse).append("]");
		if(org.apache.commons.lang.StringUtils.isNotBlank(termCode))
			errorMsg.append(" [Term Code: ").append(termCode).append("]");
		if(org.apache.commons.lang.StringUtils.isNotBlank(sectionCode))
			errorMsg.append(" [Section Code: ").append(sectionCode).append("]");
		return errorMsg.toString();
	}

	// Hopefully temporary hack to work around historically imprecise error representations in
	// portlet render/action handlers. Basically just need a way to distinguish between errors
	// which we've handled but want to represent as such the end users in a rather specific way
	// and Everything Else, which we want to represent to end users very generically.
	private static final class EarlyAlertPortletControllerRuntimeException extends RuntimeException {

		public EarlyAlertPortletControllerRuntimeException() {
			super();
		}


		public EarlyAlertPortletControllerRuntimeException(String message) {
			super(message);
		}


		public EarlyAlertPortletControllerRuntimeException(String message, Throwable cause) {
			super(message, cause);
		}


		public EarlyAlertPortletControllerRuntimeException(Throwable cause) {
			super(cause);
		}
	}
}