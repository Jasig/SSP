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
package org.jasig.ssp.web.api.tool;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.tool.PersonToolTOFactory;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.service.tool.PersonToolService;
import org.jasig.ssp.transferobject.tool.PersonToolTO;
import org.jasig.ssp.web.api.AbstractPersonAssocController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/1/person/{personId}/tool")
public class PersonToolController
		extends AbstractPersonAssocController<PersonTool, PersonToolTO> {

	protected PersonToolController() {
		super(PersonTool.class, PersonToolTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonToolController.class);

	@Autowired
	protected transient PersonToolService service;

	@Autowired
	protected transient PersonToolTOFactory factory;

	@Override
	protected PersonToolService getService() {
		return service;
	}

	@Override
	protected TOFactory<PersonToolTO, PersonTool> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String permissionBaseName() {
		return "TOOL";
	}
}
