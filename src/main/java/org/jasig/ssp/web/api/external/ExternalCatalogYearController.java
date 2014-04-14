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

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.factory.external.ExternalDivisionTOFactory;
import org.jasig.ssp.factory.external.ExternalCatalogYearTOFactory;
import org.jasig.ssp.model.external.ExternalCatalogYear;
import org.jasig.ssp.model.external.ExternalDivision;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalDivisionService;
import org.jasig.ssp.service.external.ExternalCatalogYearService;
import org.jasig.ssp.transferobject.external.ExternalCatalogYearTO;
import org.jasig.ssp.transferobject.external.ExternalDivisionTO;
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
@RequestMapping("/1/reference/catalogYear")
public class ExternalCatalogYearController extends AbstractExternalController<ExternalCatalogYearTO, ExternalCatalogYear> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalCatalogYearController.class);

	@Autowired
	protected transient ExternalCatalogYearService service;

	@Override
	protected ExternalCatalogYearService getService() {
		return service;
	}

	@Autowired
	protected transient ExternalCatalogYearTOFactory factory;

	@Override
	protected ExternalCatalogYearTOFactory getFactory() {
		return factory;
	}

	protected ExternalCatalogYearController() {
		super(ExternalCatalogYearTO.class, ExternalCatalogYear.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<ExternalCatalogYearTO> getAllCatalogYears() {
		List<ExternalCatalogYearTO> response = new ArrayList<ExternalCatalogYearTO>();
		List<ExternalCatalogYear> allCatalogYears = getService().getAll();
		for (ExternalCatalogYear externalCatalogYear : allCatalogYears) {
			response.add(getFactory().from(externalCatalogYear));
		}
		return response;
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ExternalCatalogYearTO get(final @PathVariable String code) throws ObjectNotFoundException,
			ValidationException {
		final ExternalCatalogYear model = getService().getByCode(code);
		if (model == null) {
			return null;
		}
		return new ExternalCatalogYearTO(model);
	}
	
	
}