package org.jasig.ssp.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.AppointmentDao;
import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppointmentServiceImpl
		extends AbstractPersonAssocAuditableService<Appointment>
		implements AppointmentService {

	@Autowired
	private transient AppointmentDao dao;

	@Override
	protected PersonAssocAuditableCrudDao<Appointment> getDao() {
		return dao;
	}

	@Override
	public Appointment getCurrentAppointmentForPerson(final Person person) {
		return dao.getCurrentAppointmentForPerson(person);
	}

	@Override
	public Map<UUID, Appointment> getCurrentAppointmentForPeopleIds(
			final Collection<UUID> peopleIds) {
		return dao.getCurrentAppointmentForPeopleIds(peopleIds);
	}

}
