package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.PersonalityTypeDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.PersonalityTypeTOFactory;
import org.jasig.ssp.model.reference.PersonalityType;
import org.jasig.ssp.transferobject.reference.PersonalityTypeTO;

@Service
@Transactional(readOnly = true)
public class PersonalityTypeTOFactoryImpl extends
		AbstractReferenceTOFactory<PersonalityTypeTO, PersonalityType>
		implements PersonalityTypeTOFactory {

	public PersonalityTypeTOFactoryImpl() {
		super(PersonalityTypeTO.class, PersonalityType.class);
	}

	@Autowired
	private transient PersonalityTypeDao dao;

	@Override
	protected PersonalityTypeDao getDao() {
		return dao;
	}

}
