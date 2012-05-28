package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.PersonSpecialServiceGroupDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.PersonSpecialServiceGroupTOFactory;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.transferobject.PersonSpecialServiceGroupTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonSpecialServiceGroupTOFactoryImpl
		extends
		AbstractAuditableTOFactory<PersonSpecialServiceGroupTO, PersonSpecialServiceGroup>
		implements PersonSpecialServiceGroupTOFactory {

	public PersonSpecialServiceGroupTOFactoryImpl() {
		super(PersonSpecialServiceGroupTO.class,
				PersonSpecialServiceGroup.class);
	}

	@Autowired
	private transient PersonSpecialServiceGroupDao dao;

	@Autowired
	private transient SpecialServiceGroupService service;

	@Autowired
	private transient PersonService personService;

	@Override
	protected PersonSpecialServiceGroupDao getDao() {
		return dao;
	}

	@Override
	public PersonSpecialServiceGroup from(
			final PersonSpecialServiceGroupTO tObject)
			throws ObjectNotFoundException {
		final PersonSpecialServiceGroup model = super.from(tObject);

		model.setSpecialServiceGroup((tObject.getSpecialServiceGroupId() == null) ? null
				: service.get(tObject.getSpecialServiceGroupId()));

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		return model;
	}
}
