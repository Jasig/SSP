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

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.TagTOFactory;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.reference.TagService;
import org.jasig.ssp.transferobject.reference.TagTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	protected AuditableCrudService<Tag> getService() {
		return service;
	}

	@Autowired
	protected transient TagTOFactory factory;

	@Override
	protected TOFactory<TagTO, Tag> getFactory() {
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
}