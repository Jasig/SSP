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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.ElectiveTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Elective;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ElectiveService;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ElectiveTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/1/reference/elective")
@PreAuthorize(Permission.SECURITY_REFERENCE_MAP_WRITE)
public class ElectiveController
		extends
		AbstractAuditableReferenceController<Elective, ElectiveTO> {

	@Autowired
	protected transient ElectiveService service;

	@Override
	protected AuditableCrudService<Elective> getService() {
		return service;
	}

	@Autowired
	protected transient ElectiveTOFactory factory;

	@Override
	protected TOFactory<ElectiveTO, Elective> getFactory() {
		return factory;
	}

	protected ElectiveController() {
		super(Elective.class, ElectiveTO.class);
	}
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ElectiveController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize(Permission.SECURITY_REFERENCE_WRITE)
	public @ResponseBody
	ElectiveTO save(@Valid @RequestBody final List<String> jsonList)
			throws ValidationException, ObjectNotFoundException {
		List<ElectiveTO> electives = new ArrayList<ElectiveTO>();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			for(String json: jsonList) {	
				ElectiveTO elective = mapper.readValue(json, ElectiveTO.class);
				electives.add(elective);				
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elective model = null;
		Elective savedElective = null;
		if(electives.size() > 0) {
			for(ElectiveTO electiveTO: electives) {
				model = getFactory().from(electiveTO);
				savedElective = getService().save(model);
			}
		}
		
		if(savedElective != null) {
			return this.instantiateTO(model);
		}
		

		return null;
	}

	@Override
	protected String getDefaultSortColumn() {
		return "sortOrder";
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
	public @ResponseBody ElectiveTO create(@Valid @RequestBody final ElectiveTO obj)
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
	public @ResponseBody ElectiveTO save(@PathVariable final UUID id, @Valid @RequestBody final ElectiveTO obj)
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
