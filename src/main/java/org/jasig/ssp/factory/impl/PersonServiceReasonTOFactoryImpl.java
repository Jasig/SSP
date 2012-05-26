package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonServiceReasonDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonServiceReasonTOFactory;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ServiceReasonService;
import org.jasig.ssp.transferobject.PersonServiceReasonTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonServiceReasonTOFactoryImpl
		extends
		AbstractAuditableTOFactory<PersonServiceReasonTO, PersonServiceReason>
		implements PersonServiceReasonTOFactory {

	public PersonServiceReasonTOFactoryImpl() {
		super(PersonServiceReasonTO.class,
				PersonServiceReason.class);
	}

	@Autowired
	private transient PersonServiceReasonDao dao;

	@Autowired
	private transient ServiceReasonService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonServiceReasonDao getDao() {
		return dao;
	}

	@Override
	public PersonServiceReason from(
			final PersonServiceReasonTO tObject)
			throws ObjectNotFoundException {
		final PersonServiceReason model = super.from(tObject);

		model.setServiceReason((tObject.getServiceReasonId() == null) ? null
				: service.get(tObject.getServiceReasonId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}
