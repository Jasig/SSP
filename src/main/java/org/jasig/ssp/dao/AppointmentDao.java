package org.jasig.ssp.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentDao
		extends AbstractPersonAssocAuditableCrudDao<Appointment>
		implements PersonAssocAuditableCrudDao<Appointment> {

	protected AppointmentDao() {
		super(Appointment.class);
	}

	public Appointment getCurrentAppointmentForPerson(final Person person) {

		Criteria query = createCriteria();

		// Active appt in the future for the given person
		query.add(Restrictions.eq("person", person));
		query.add(Restrictions.ge("startTime", new Date()));
		query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

		// only return the first appt in the future
		query.addOrder(Order.asc("startTime"));
		query.setMaxResults(1);

		final Appointment futureAppt = (Appointment) query.uniqueResult();

		// if there is no future appt, pull the most recent one
		if (futureAppt == null) {
			query = createCriteria();

			// Active appt in the past for the given person
			query.add(Restrictions.eq("person", person));
			query.add(Restrictions.le("startTime", new Date()));
			query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

			// only return the first appt in the future
			query.addOrder(Order.desc("startTime"));
			query.setMaxResults(1);

			return (Appointment) query.uniqueResult();
		} else {
			return futureAppt;
		}
	}
}
