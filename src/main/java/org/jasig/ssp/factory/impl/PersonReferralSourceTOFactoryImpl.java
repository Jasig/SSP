package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonReferralSourceDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonReferralSourceTOFactory;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ReferralSourceService;
import org.jasig.ssp.transferobject.PersonReferralSourceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonReferralSourceTOFactoryImpl
		extends
		AbstractAuditableTOFactory<PersonReferralSourceTO, PersonReferralSource>
		implements PersonReferralSourceTOFactory {

	public PersonReferralSourceTOFactoryImpl() {
		super(PersonReferralSourceTO.class,
				PersonReferralSource.class);
	}

	@Autowired
	private transient PersonReferralSourceDao dao;

	@Autowired
	private transient ReferralSourceService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonReferralSourceDao getDao() {
		return dao;
	}

	@Override
	public PersonReferralSource from(
			final PersonReferralSourceTO tObject)
			throws ObjectNotFoundException {
		final PersonReferralSource model = super.from(tObject);

		model.setReferralSource((tObject.getReferralSourceId() == null) ? null
				: service.get(tObject.getReferralSourceId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}
