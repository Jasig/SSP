package org.jasig.ssp.portlet.alert;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
			@RequestParam final String formattedCourse) {
		Person user = null;
		FacultyCourse course = null;
		Person student = null;
		try {
			user = personService.personFromUsername(req.getRemoteUser());
			course = facultyCourseService.getCourseByFacultySchoolIdAndFormattedCourse(
					user.getSchoolId(), formattedCourse);
			/*
			 * NB:  It's on us to translate from schoolId <-> studentId (SSP 
			 * UUID) at this point in the Early Alert process.  Previous APIs 
			 * user the former where following APIs use the later.
			 */
			student = personService.getByStudentId(schoolId);
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
		Map<String,Object> model = new HashMap<String,Object>();
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
			rslt = personService.personFromUsername(req.getRemoteUser());
		} catch (ObjectNotFoundException e) {
			throw new RuntimeException("Unrecognized person:  " + req.getRemoteUser());
		}
		return rslt;
	}
}