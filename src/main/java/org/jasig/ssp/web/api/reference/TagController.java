/**
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

import java.util.List;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.TagTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Elective;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.TagService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.ElectiveTO;
import org.jasig.ssp.transferobject.reference.TagTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Tag controller
 * 
 * @author archna.jindal
 * 
 */
@Controller
@RequestMapping("/1/reference/tag")
public class TagController
		extends
		AbstractAuditableReferenceController<Tag, TagTO> {

	@Autowired
	protected transient TagService service;

	@Override
	protected TagService getService() {
		return service;
	}

	@Autowired
	protected transient TagTOFactory factory;

	@Override
	protected TagTOFactory getFactory() {
		return factory;
	}

	protected TagController() {
		super(Tag.class, TagTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentTypeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<TagTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		final PagingWrapper<Tag> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						status == null ? ObjectStatus.ALL : status, start,
						limit, sort, sortDirection, "name"));

		return new PagedResponse<TagTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));

	}
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<TagTO> facetSearch(
			final @RequestParam(required = false) String programCode,
			final @RequestParam(required = false) String termCode) {

		List<Tag> data = getService().facetSearch(programCode,termCode);

		return getFactory().asTOList(data);

	}	
}