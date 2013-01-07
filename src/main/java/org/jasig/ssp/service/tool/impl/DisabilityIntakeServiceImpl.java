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
package org.jasig.ssp.service.tool.impl;

import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.DisabilityIntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.tool.DisabilityIntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Disability Intake service implementation
 */
@Service
@Transactional
public class DisabilityIntakeServiceImpl implements DisabilityIntakeService {

	@Autowired
	private transient PersonService personService;

	/**
	 * Load the specified Person.
	 * 
	 * Be careful when walking the tree to avoid performance issues that can
	 * arise if eager fetching from the database layer is not used
	 * appropriately.
	 */
	@Override
	public DisabilityIntakeForm loadForPerson(final UUID studentId)
			throws ObjectNotFoundException {
		final DisabilityIntakeForm form = new DisabilityIntakeForm();

		final Person person = personService.get(studentId);
		form.setPerson(person);

		return form;
	}

	/**
	 * Persist the form contents
	 */
	@Override
	public boolean save(final DisabilityIntakeForm form) throws ObjectNotFoundException {
		if (form.getPerson() == null) {
			throw new ObjectNotFoundException(
					"Missing (null) Person.",
					"Person");
		}

		final Person person = form.getPerson();

		// Save changes to persistent storage.
		personService.save(person);

		return true;
	}

}