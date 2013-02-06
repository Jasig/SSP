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

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideDetailsTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailsTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.jasig.ssp.web.api.reference.AbstractAuditableReferenceController;
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
@PreAuthorize("hasRole('ROLE_PERSON_SEARCH_READ')")
@RequestMapping("/1/selfHelpGuides/search")
public class SelfHelpGuidesAdminController extends AbstractAuditableReferenceController<SelfHelpGuide, SelfHelpGuideDetailsTO> {

	protected SelfHelpGuidesAdminController(
			Class<SelfHelpGuide> persistentClass,
			Class<SelfHelpGuideDetailsTO> transferObjectClass) {
		super(persistentClass, transferObjectClass);
	}
	protected SelfHelpGuidesAdminController() {
		super(SelfHelpGuide.class, SelfHelpGuideDetailsTO.class);
	}
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuidesAdminController.class);

	@Autowired
	private transient SelfHelpGuideService service;
	
	@Autowired
	private transient SelfHelpGuideDetailsTOFactory selfHelpGuideTOFactory;;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@RequestMapping(method = RequestMethod.GET)
	@Override
	public @ResponseBody
	PagedResponse<SelfHelpGuideDetailsTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) 
			 {
		
		return super.getAll(ObjectStatus.ALL, null, null, null, null);
	}

	@Override
	protected AuditableCrudService<SelfHelpGuide> getService() {
		return service;
	}

	@Override
	protected TOFactory<SelfHelpGuideDetailsTO, SelfHelpGuide> getFactory() {
		return selfHelpGuideTOFactory;
	}	
}