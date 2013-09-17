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
import org.jasig.ssp.factory.reference.MilitaryAffiliationTOFactory;
import org.jasig.ssp.factory.reference.RegistrationLoadTOFactory;
import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.model.reference.RegistrationLoad;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.MilitaryAffiliationService;
import org.jasig.ssp.service.reference.RegistrationLoadService;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;
import org.jasig.ssp.transferobject.reference.RegistrationLoadTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/reference/registrationLoad")
public class RegistrationLoadController
		extends
		AbstractAuditableReferenceController<RegistrationLoad, RegistrationLoadTO> {

	@Autowired
	protected transient RegistrationLoadService service;

	@Override
	protected AuditableCrudService<RegistrationLoad> getService() {
		return service;
	}
  
	@Autowired
	protected transient RegistrationLoadTOFactory factory;

	@Override
	protected TOFactory<RegistrationLoadTO, RegistrationLoad> getFactory() {
		return factory;
	}

	protected RegistrationLoadController() {
		super(RegistrationLoad.class, RegistrationLoadTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegistrationLoadController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
