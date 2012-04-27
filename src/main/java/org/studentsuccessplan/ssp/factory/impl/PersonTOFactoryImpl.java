package org.studentsuccessplan.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.PersonDao;
import org.studentsuccessplan.ssp.factory.AbstractAuditableTOFactory;
import org.studentsuccessplan.ssp.factory.PersonTOFactory;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.PersonTO;

@Service
@Transactional(readOnly = true)
public class PersonTOFactoryImpl extends
		AbstractAuditableTOFactory<PersonTO, Person>
		implements PersonTOFactory {

	public PersonTOFactoryImpl() {
		super(PersonTO.class, Person.class);
	}

	@Autowired
	private PersonDao dao;

	@Override
	protected PersonDao getDao() {
		return dao;
	}

	@Override
	public Person from(PersonTO tObject)
			throws ObjectNotFoundException {
		Person model = super.from(tObject);

		model.setFirstName(tObject.getFirstName());
		model.setMiddleInitial(tObject.getMiddleInitial());
		model.setLastName(tObject.getLastName());
		model.setBirthDate(tObject.getBirthDate());
		model.setPrimaryEmailAddress(tObject.getPrimaryEmailAddress());
		model.setSecondaryEmailAddress(tObject.getSecondaryEmailAddress());
		model.setUsername(tObject.getUsername());
		model.setUserId(tObject.getUserId());
		model.setHomePhone(tObject.getHomePhone());
		model.setWorkPhone(tObject.getWorkPhone());
		model.setCellPhone(tObject.getCellPhone());
		model.setAddressLine1(tObject.getAddressLine1());
		model.setAddressLine2(tObject.getAddressLine2());
		model.setCity(tObject.getCity());
		model.setState(tObject.getState());
		model.setZipCode(tObject.getZipCode());
		model.setPhotoUrl(tObject.getPhotoUrl());
		model.setSchoolId(tObject.getSchoolId());
		model.setEnabled(tObject.isEnabled());

		return model;
	}

}
