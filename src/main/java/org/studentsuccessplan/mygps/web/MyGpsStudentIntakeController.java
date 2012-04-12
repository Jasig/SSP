package org.studentsuccessplan.mygps.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.mygps.business.StudentIntakeFormManager;
import org.studentsuccessplan.mygps.model.transferobject.FormTO;

@Controller
@RequestMapping("/mygps/intake")
public class MyGpsStudentIntakeController extends AbstractMyGpsController {

	@Autowired
	private StudentIntakeFormManager studentIntakeFormManager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsStudentIntakeController.class);

	protected void setManager(StudentIntakeFormManager manager) {
		this.studentIntakeFormManager = manager;
	}

	@RequestMapping(value = "/getForm", method = RequestMethod.GET)
	public @ResponseBody
	FormTO getForm() throws Exception {
		try {
			return studentIntakeFormManager.populate();
		} catch (Exception e) {
			LOGGER.error("ERROR : getForm() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Boolean saveForm(@RequestBody FormTO formTO) throws Exception {

		try {
			studentIntakeFormManager.save(formTO);
			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : saveForm() : {}", e.getMessage(), e);
			throw e;
		}
	}
}
