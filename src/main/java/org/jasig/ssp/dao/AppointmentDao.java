package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.springframework.stereotype.Repository;

/**
 * Appointment DAO
 */
@Repository
public class AppointmentDao
		extends AbstractPersonAssocAuditableCrudDao<Appointment>
		implements PersonAssocAuditableCrudDao<Appointment> {

	protected AppointmentDao() {
		super(Appointment.class);
	}

	public Appointment getCurrentAppointmentForPerson(final Person person) {

		final Criteria query = createCriteria();

		// Active appointment for the given person
		query.add(Restrictions.eq("person", person));
		query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));

		// only return the most recently modified appointment
		query.addOrder(Order.asc("modifiedDate"));
		query.setMaxResults(1);

		return (Appointment) query.uniqueResult();
	}
}
