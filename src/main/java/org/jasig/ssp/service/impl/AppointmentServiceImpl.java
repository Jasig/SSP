package org.jasig.ssp.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.AppointmentDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
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
}
