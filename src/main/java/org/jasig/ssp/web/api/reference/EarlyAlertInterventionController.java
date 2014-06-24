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
 
 /*
 * IRSC CUSTOMIZATIONS
 * 06/16/2014 - Jonathan Hart IRSC TAPS 20140039 - Created EarlyAlertInterventionController.java
 */
package org.jasig.ssp.web.api.reference;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertInterventionTOFactory;
import org.jasig.ssp.model.reference.EarlyAlertIntervention;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.EarlyAlertInterventionService;
import org.jasig.ssp.transferobject.reference.EarlyAlertInterventionTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating EarlyAlertIntervention reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/earlyAlertIntervention</code>
 * 
 * Based on EarlyAlertSuggestion by @author jon.adams
 */
@Controller
@RequestMapping("/1/reference/earlyAlertIntervention")
public class EarlyAlertInterventionController
		extends
		AbstractAuditableReferenceController<EarlyAlertIntervention, EarlyAlertInterventionTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient EarlyAlertInterventionService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient EarlyAlertInterventionTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertInterventionController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected EarlyAlertInterventionController() {
		super(EarlyAlertIntervention.class, EarlyAlertInterventionTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<EarlyAlertIntervention> getService() {
		return service;
	}

	@Override
	protected TOFactory<EarlyAlertInterventionTO, EarlyAlertIntervention> getFactory() {
		return factory;
	}
}
