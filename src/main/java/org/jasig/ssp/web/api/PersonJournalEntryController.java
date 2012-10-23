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

import org.jasig.ssp.factory.JournalEntryTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Person JournalEntry controller
 */
@Controller
@RequestMapping("/1/person/{personId}/journalEntry")
public class PersonJournalEntryController extends
		AbstractRestrictedPersonAssocController<JournalEntry, JournalEntryTO> {

	/**
	 * Construct a controller instance with the specific class types used by the
	 * super class methods.
	 */
	protected PersonJournalEntryController() {
		super(JournalEntry.class, JournalEntryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonJournalEntryController.class);

	@Autowired
	private transient JournalEntryService service;

	@Autowired
	private transient JournalEntryTOFactory factory;

	@Override
	protected JournalEntryTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected JournalEntryService getService() {
		return service;
	}

	@Override
	public String permissionBaseName() {
		return "JOURNAL_ENTRY";
	}
}