package org.jasig.ssp.service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.Person;

public interface AppointmentService extends
		PersonAssocAuditableService<Appointment> {

	Appointment getCurrentAppointmentForPerson(final Person person);

	Map<UUID, Appointment> getCurrentAppointmentForPeopleIds(
			final Collection<UUID> peopleIds);
}
