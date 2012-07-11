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
		model.setExpirationDate(tObject.getExpirationDate());

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}

}
