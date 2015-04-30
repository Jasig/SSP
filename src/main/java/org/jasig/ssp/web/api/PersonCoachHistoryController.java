/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api; // NOPMD

import com.google.common.collect.Lists;
import org.jasig.ssp.dao.PersonCoachAuditDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonCoachHistoryService;
import org.jasig.ssp.transferobject.PersonCoachAuditTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Services to manipulate Coach Audit/History of coach changes.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/coachHistory</code>
 */
@Controller
@RequestMapping("/1/person/{personId}/coachHistory")
public class PersonCoachHistoryController extends AbstractBaseController{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonCoachHistoryController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Autowired
	PersonCoachAuditDao personCoachAuditDao;

	@Autowired
	PersonCoachHistoryService personCoachHistoryService;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_READ') or hasRole('ROLE_PERSON_FILTERED_READ')")
	public @ResponseBody PersonCoachAuditTO getCurrent(@PathVariable final UUID personId)
			throws ObjectNotFoundException {

		if (personId != null) {

			this.personCoachAuditDao.auditBatchCoachAssignment(new Person(personId), new ArrayList<String>());

			return personCoachHistoryService.getLastCoachHistory(personId);
		}

		return new PersonCoachAuditTO();
	}
}
