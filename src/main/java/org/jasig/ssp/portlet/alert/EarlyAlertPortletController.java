package org.jasig.ssp.portlet.alert;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class EarlyAlertPortletController {
	
	private static final String KEY_STUDENT_ID = "studentId";
	private static final String KEY_FORMATTED_COURSE = "formattedCourse";

	@Autowired
	private PersonService personService;

	@RenderMapping
	public ModelAndView showRoster(PortletRequest req) {
		Person user = null;
		try {
			user = personService.personFromUsername(req.getRemoteUser());
		} catch (ObjectNotFoundException e) {
			throw new RuntimeException("Unrecognized person:  " + req.getRemoteUser());
		}
		return new ModelAndView("ea-roster", "user", user);
//		return new ModelAndView("ea-roster");
	}

	@RenderMapping(params = "action=enterAlert")
	public ModelAndView showForm(@RequestParam final String studentId, @RequestParam final String formattedCourse) {
		Map<String,String> model = new HashMap<String,String>();
		model.put(KEY_STUDENT_ID, studentId);
		model.put(KEY_FORMATTED_COURSE, formattedCourse);
		return new ModelAndView("ea-form", model);
	}

	@RenderMapping(params = "confirm=true")
	public ModelAndView confirm(@RequestParam final String studentName) {
		return new ModelAndView("ea-roster", "studentName", studentName);
	}
}