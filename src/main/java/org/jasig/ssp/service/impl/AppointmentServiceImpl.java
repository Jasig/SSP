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
package org.jasig.ssp.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.AppointmentDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

@Service
@Transactional
public class AppointmentServiceImpl
		extends AbstractPersonAssocAuditableService<Appointment>
		implements AppointmentService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private transient AppointmentDao dao;

	@Autowired
	private transient PersonService personService;

	@Override
	protected AppointmentDao getDao() {
		return dao;
	}

	@Override
	public Appointment getCurrentAppointmentForPerson(final Person person) {
		return dao.getCurrentAppointmentForPerson(person);
	}

	@Override
	public Map<UUID, Appointment> getCurrentAppointmentForPeopleIds(
			final Collection<UUID> peopleIds) {
		final Map<UUID, Appointment> appts = Maps.newHashMap();

		for (UUID uuid : peopleIds) {
			try {
				final Appointment appt = getCurrentAppointmentForPerson(personService
						.get(uuid));
				if (appt != null) {
					appts.put(uuid, appt);
				}
			} catch (ObjectNotFoundException e) {
				LOGGER.error("Unable to find person by id: " + uuid.toString());
			}
		}

		return appts;
	}
	
	@Override
	public Map<UUID, Date> getCurrentAppointmenDateForPeopleIds(
			final Collection<UUID> peopleIds) {
		
		
		final Map<UUID, Date> appts = dao.getCurrentAppointmentDatesForPeopleIds(peopleIds);

		return appts;
	}

	@Override
	public Appointment create(final Appointment obj)
			throws ObjectNotFoundException, ValidationException {

		final Date now = new Date();

		if (obj.getStartTime().before(now) || obj.getEndTime().before(now)) {
			throw new ValidationException(
					"Start time and end time must be in the future");
		}

		if (obj.getEndTime().before(obj.getStartTime())) {
			throw new ValidationException(
					"The start time must be before the end time.");
		}

		return super.create(obj);
	}

}
