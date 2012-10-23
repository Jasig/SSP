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
package org.jasig.ssp.web.api.external;

import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.factory.external.TermTOFactory;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.transferobject.external.TermTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/reference/term")
public class TermController extends AbstractExternalController<TermTO, Term> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TermController.class);

	@Autowired
	protected transient TermService service;

	@Override
	protected TermService getService() {
		return service;
	}

	@Autowired
	protected transient TermTOFactory factory;

	@Override
	protected ExternalTOFactory<TermTO, Term> getFactory() {
		return factory;
	}

	protected TermController() {
		super(TermTO.class, Term.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param code
	 *            The specific code to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	TermTO get(final @PathVariable String code) throws ObjectNotFoundException,
			ValidationException {
		final Term model = getService().getByCode(code);
		if (model == null) {
			return null;
		}

		return super.instantiateTO(model);
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	TermTO currentTerm() throws ObjectNotFoundException,
			ValidationException {		
		final Term model = getService().getCurrentTerm();
		if (model == null) {
			return null;
		}

		return super.instantiateTO(model);
	}
}