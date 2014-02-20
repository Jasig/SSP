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

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.CompletedItemTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.CompletedItem;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.CompletedItemService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.CompletedItemTO;
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

@Controller
@RequestMapping("/1/reference/completedItem")
public class CompletedItemController
		extends
		AbstractAuditableReferenceController<CompletedItem, CompletedItemTO> {

	@Autowired
	protected transient CompletedItemService service;

	@Override
	protected AuditableCrudService<CompletedItem> getService() {
		return service;
	}

	@Autowired
	protected transient CompletedItemTOFactory factory;

	@Override
	protected TOFactory<CompletedItemTO, CompletedItem> getFactory() {
		return factory;
	}

	protected CompletedItemController() {
		super(CompletedItem.class, CompletedItemTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CompletedItemController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<CompletedItemTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		final PagingWrapper<CompletedItem> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						status == null ? ObjectStatus.ALL : status, start,
						limit, sort, sortDirection, "name"));

		return new PagedResponse<CompletedItemTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));

	}
}
