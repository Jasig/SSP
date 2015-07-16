/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelOptionTOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelOptionTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/1/reference/confidentialityLevel")
@PreAuthorize(Permission.SECURITY_REFERENCE_SECURITY_WRITE)
public class ConfidentialityLevelController
		extends
		AbstractAuditableReferenceController<ConfidentialityLevel, ConfidentialityLevelTO> {

	@Autowired
	protected transient ConfidentialityLevelService service;

	@Override
	protected AuditableCrudService<ConfidentialityLevel> getService() {
		return service;
	}

	@Autowired
	protected transient ConfidentialityLevelOptionTOFactory optionFactory;
	
	@Autowired
	protected transient ConfidentialityLevelTOFactory factory;

	@Override
	protected TOFactory<ConfidentialityLevelTO, ConfidentialityLevel> getFactory() {
		return factory;
	}

	protected ConfidentialityLevelController() {
		super(ConfidentialityLevel.class, ConfidentialityLevelTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfidentialityLevelController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@RequestMapping(value="/options", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<ConfidentialityLevelOptionTO> getAllOptions() {
		List<DataPermissions> availableOptions = service.getAvailableConfidentialityLevelOptions();
		return new PagedResponse<ConfidentialityLevelOptionTO>(true, new Long(availableOptions.size()), optionFactory.asTOList(availableOptions));
	}

	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 *
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified data contains an id (since it shouldn't).
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ConfidentialityLevelTO create(@Valid @RequestBody final ConfidentialityLevelTO obj)
			throws ObjectNotFoundException,	ValidationException {
		return super.create(obj);
	}

	/**
	 * Persist any changes to the specified instance.
	 *
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance to persist.
	 * @return The update data object instance.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified id is null.
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody ConfidentialityLevelTO save(@PathVariable final UUID id, @Valid @RequestBody final ConfidentialityLevelTO obj)
			throws ValidationException, ObjectNotFoundException {
		return super.save(id, obj);
	}

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 *
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		return super.delete(id);
	}
}
