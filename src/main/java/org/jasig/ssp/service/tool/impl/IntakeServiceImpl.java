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
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.tool.IntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Intake service implementation
 */
@Service
@Transactional
public class IntakeServiceImpl implements IntakeService {

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
	public IntakeForm loadForPerson(final UUID studentId)
			throws ObjectNotFoundException {
		final IntakeForm form = new IntakeForm();

		final Person person = personService.get(studentId);
		form.setPerson(person);

		return form;
	}

	/**
	 * Persist the form contents
	 */
	@Override
	public boolean save(final IntakeForm form) throws ObjectNotFoundException {
		if (form.getPerson() == null) {
			throw new ObjectNotFoundException(
					"Missing (null) Person.",
					"Person");
		}

		final Person person = form.getPerson();
		person.setStudentIntakeCompleteDate(new Date());

		// Save changes to persistent storage.
		personService.save(person);

		return true;
	}

}
