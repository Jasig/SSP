package org.jasig.mygps.web;

import org.jasig.mygps.business.StudentIntakeFormManager;
import org.jasig.mygps.model.transferobject.FormTO;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.web.api.AbstractBaseController;
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
public class MyGpsStudentIntakeController extends AbstractBaseController {

	@Autowired
	private transient StudentIntakeFormManager studentIntakeFormManager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsStudentIntakeController.class);

	protected void setManager(final StudentIntakeFormManager manager) {
		studentIntakeFormManager = manager;
	}

	@PreAuthorize("hasRole('ROLE_STUDENT_INTAKE_READ')")
	@RequestMapping(value = "/getForm", method = RequestMethod.GET)
	public @ResponseBody
	FormTO getForm() throws ObjectNotFoundException {
		return studentIntakeFormManager.populate();
	}

	@PreAuthorize("hasRole('ROLE_STUDENT_INTAKE_WRITE')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	Boolean saveForm(final @RequestBody FormTO formTO)
			throws ObjectNotFoundException {
		studentIntakeFormManager.save(formTO);
		return true;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
