package org.jasig.ssp.dao;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.Person;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

@Repository
public class AppointmentDao
		extends AbstractPersonAssocAuditableCrudDao<Appointment>
		implements PersonAssocAuditableCrudDao<Appointment> {

	protected AppointmentDao() {
		super(Appointment.class);
	}

	public Appointment getCurrentAppointmentForPerson(final Person person) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("person", person));
		query.add(Restrictions.isNull("expirationDate"));
		return (Appointment) query.uniqueResult();
	}

	public Map<UUID, Appointment> getCurrentAppointmentForPeopleIds(
			final Collection<UUID> peopleIds) {

		final Map<UUID, Appointment> apptForPeopleId = Maps.newHashMap();

		final Criteria query = createCriteria();
		query.add(Restrictions.in("person.id", peopleIds));
		query.add(Restrictions.isNull("expirationDate"));

		@SuppressWarnings("unchecked")
		final Collection<Appointment> appts = query.list();
		for (Appointment appt : appts) {
			apptForPeopleId.put(appt.getPerson().getId(), appt);
		}

		return apptForPeopleId;
	}
}
