package org.studentsuccessplan.mygps.portlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.transferobject.ServiceResponse;

public class AbstractMyGpsController {

	@Autowired
	protected SecurityService securityService;

	private static final Logger LOGGER =
			LoggerFactory.getLogger(AbstractMyGpsController.class);

	@PreAuthorize("permitAll")
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ServiceResponse handleNotFound(ObjectNotFoundException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	@PreAuthorize("permitAll")
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody
	ServiceResponse handleAccessDenied(AccessDeniedException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	@PreAuthorize("permitAll")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ServiceResponse handleValidationError(
			final MethodArgumentNotValidException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e);
	}

	@PreAuthorize("permitAll")
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

}
