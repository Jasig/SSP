package org.jasig.ssp.web.api;

import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/lti/launch")
public class LtiLaunchController extends AbstractBaseController {

	@Autowired
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerController.class);

	// TODO all request handling here is strictly POC for purposes of seeing what SecurityService thinks
	// an LTI/Oauth-authenticated request looks like

	@PreAuthorize(Permission.IS_AUTHENTICATED)
	@RequestMapping(method = RequestMethod.POST, value = "/live")
	public @ResponseBody
	Object handlePost()
			throws ObjectNotFoundException, ValidationException {

		final SspUser sspUser = securityService.currentlyAuthenticatedUser();
		return sspUser;

	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
