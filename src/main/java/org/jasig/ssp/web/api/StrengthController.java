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

import org.jasig.ssp.factory.StrengthTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Strength;
import org.jasig.ssp.service.StrengthService;
import org.jasig.ssp.transferobject.StrengthTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Strength controller
 */
@Controller
@RequestMapping("/1/person/{personId}/strength")
public class StrengthController
		extends AbstractRestrictedPersonAssocController<Strength, StrengthTO> {

	/**
	 * Construct an instance with specific classes for use by the super class
	 * methods.
	 */
	protected StrengthController() {
		super(Strength.class, StrengthTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrengthController.class);

	@Autowired
	protected transient StrengthService service;

	@Autowired
	protected transient StrengthTOFactory factory;

	@Override
	protected StrengthService getService() {
		return service;
	}

	@Override
	protected TOFactory<StrengthTO, Strength> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String permissionBaseName() {
		return "STRENGTH";
	}
}