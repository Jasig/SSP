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
import org.jasig.ssp.factory.reference.CampusTOFactory;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.transferobject.reference.CampusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Some basic methods for manipulating Campus reference data.
 * <p>
 * Mapped to URI path <code>/1/reference/campus</code>
 * 
 * @author jon.adams
 */
@Controller
@RequestMapping("/1/reference/campus")
public class CampusController
		extends
		AbstractAuditableReferenceController<Campus, CampusTO> {

	/**
	 * Auto-wired service-layer instance.
	 */
	@Autowired
	protected transient CampusService service;

	/**
	 * Auto-wired transfer object factory.
	 */
	@Autowired
	protected transient CampusTOFactory factory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CampusController.class);

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 */
	protected CampusController() {
		super(Campus.class, CampusTO.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected AuditableCrudService<Campus> getService() {
		return service;
	}

	@Override
	protected TOFactory<CampusTO, Campus> getFactory() {
		return factory;
	}
}
