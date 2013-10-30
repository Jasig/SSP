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
package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.BlurbTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Blurb;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.BlurbService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.BlurbTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/blurb")
public class BlurbController {

	@Autowired
	protected transient BlurbService service;

	protected BlurbService getService() {
		return service;
	}

	@Autowired
	protected transient BlurbTOFactory factory;

	protected TOFactory<BlurbTO, Blurb> getFactory() {
		return factory;
	}

	protected BlurbController() {
	}
	
	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(BlurbController.class);	
	@RequestMapping(method = RequestMethod.GET)
	
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<BlurbTO> getAll(
			final @RequestParam(required = false) String code,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {
 
		
		final PagingWrapper<Blurb> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ALL, start,
						limit, sort, sortDirection, "code"),code);
		return new PagedResponse<BlurbTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));

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
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	BlurbTO save(@PathVariable final UUID id, @Valid @RequestBody final BlurbTO obj)
			throws ValidationException, ObjectNotFoundException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}

		final Blurb model = getFactory().from(obj);

		final Blurb savedT = getService().save(model);
		if (null != savedT) {
			return this.instantiateTO(model);
		}

		return null;
	}	

	private BlurbTO instantiateTO(final Blurb model) throws ValidationException {
		BlurbTO out;
		out =new BlurbTO();
		out.from(model);
		return out;
	}
}
