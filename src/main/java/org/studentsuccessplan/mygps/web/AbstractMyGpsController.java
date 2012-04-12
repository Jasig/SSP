package org.studentsuccessplan.mygps.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.service.SecurityService;

public class AbstractMyGpsController {

	@Autowired
	protected SecurityService securityService;

	private static final Logger LOGGER =
			LoggerFactory.getLogger(AbstractMyGpsController.class);

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	String handleException(Exception e, HttpServletResponse response) {
		LOGGER.error("ERROR : handleException()", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return e.getMessage();
	}

}
