package org.studentsuccessplan.mygps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.web.api.BaseController;

public abstract class AbstractMyGpsController extends BaseController {

	@Autowired
	protected SecurityService securityService;
}
