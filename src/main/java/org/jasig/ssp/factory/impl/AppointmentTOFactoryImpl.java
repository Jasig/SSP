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
package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.AppointmentDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.AppointmentTOFactory;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.AppointmentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AppointmentTOFactoryImpl
		extends AbstractAuditableTOFactory<AppointmentTO, Appointment>
		implements AppointmentTOFactory {

	public AppointmentTOFactoryImpl() {
		super(AppointmentTO.class, Appointment.class);
	}

	@Autowired
	private transient AppointmentDao dao;

	@Autowired
	private transient PersonService personService;

	@Override
	protected AppointmentDao getDao() {
		return dao;
	}

	@Override
	public Appointment from(final AppointmentTO tObject)
			throws ObjectNotFoundException {
		final Appointment model = super.from(tObject);

		model.setStartTime(tObject.getStartTime());
		model.setEndTime(tObject.getEndTime());
		model.setAttended(tObject.isAttended());
		model.setStudentIntakeRequested(tObject.isStudentIntakeRequested());
		model.setIntakeEmail(tObject.getIntakeEmail());

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}

}
