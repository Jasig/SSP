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
import org.jasig.ssp.factory.reference.ConfidentialityLevelOptionTOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelOptionTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;
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
@RequestMapping("/1/reference/confidentialityLevel")
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
}
