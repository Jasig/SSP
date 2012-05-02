package org.jasig.mygps.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.web.api.BaseController;

public abstract class AbstractMyGpsController extends BaseController {

	@Autowired
	protected SecurityService securityService;
}
