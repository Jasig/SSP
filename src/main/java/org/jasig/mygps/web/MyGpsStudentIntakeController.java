package org.jasig.mygps.web;

import org.jasig.mygps.business.StudentIntakeFormManager;
import org.jasig.mygps.model.transferobject.FormTO;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/mygps/intake")
public class MyGpsStudentIntakeController extends BaseController {

	@Autowired
	private StudentIntakeFormManager studentIntakeFormManager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsStudentIntakeController.class);

	protected void setManager(StudentIntakeFormManager manager) {
		studentIntakeFormManager = manager;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
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

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
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

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
