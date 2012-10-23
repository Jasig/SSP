/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api;

import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base controller that provides basic exceptions and loggers, that all
 * controllers in the system must extend.
 */
public abstract class AbstractBaseController {

	private static final String ERROR_PREFIX = "Error: ";

	/**
	 * Log and return an appropriate message for a page not found (HTTP 404,
	 * {@link HttpStatus#NOT_FOUND}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize(Permission.PERMIT_ALL)
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ServiceResponse handleNotFound(final ObjectNotFoundException e) {
		getLogger().error(ERROR_PREFIX, e);
		return new ServiceResponse(false, e.getMessage());
	}

	/**
	 * Log and return an appropriate message for a bad request error (
	 * {@link HttpStatus#BAD_REQUEST}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize(Permission.PERMIT_ALL)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ServiceResponse handleValidationError(
			final MethodArgumentNotValidException e) {
		getLogger().error(ERROR_PREFIX, e);
		return new ServiceResponse(false, e);
	}

	/**
	 * Log and return an appropriate message for an access denied error (
	 * {@link HttpStatus#FORBIDDEN}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize(Permission.PERMIT_ALL)
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody
	ServiceResponse handleAccessDenied(final AccessDeniedException e) {
		getLogger().error(ERROR_PREFIX, e);
		return new ServiceResponse(false, e.getMessage());
	}

	@PreAuthorize(Permission.PERMIT_ALL)
	@ExceptionHandler(ObjectExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody
	ServiceResponse handleObjectExists(final ObjectExistsException e) {
		getLogger().error(ERROR_PREFIX, e);
		return new ServiceResponse(false, e);
	}

	/**
	 * Log and return an appropriate message for an internal server error (HTTP
	 * 500, {@link HttpStatus#INTERNAL_SERVER_ERROR}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize(Permission.PERMIT_ALL)
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	ServiceResponse handle(final Exception e) {
		getLogger().error(ERROR_PREFIX, e);
		return new ServiceResponse(false, e.getMessage());
	}

	/**
	 * Retrieves a logger instance for system logging.
	 * 
	 * @return A logger instance
	 */
	protected abstract Logger getLogger();
}
