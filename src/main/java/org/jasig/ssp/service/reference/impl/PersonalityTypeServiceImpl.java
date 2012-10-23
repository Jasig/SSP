package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.PersonalityTypeDao;
import org.jasig.ssp.model.reference.PersonalityType;
import org.jasig.ssp.service.reference.PersonalityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonalityTypeServiceImpl extends
		AbstractReferenceService<PersonalityType>
		implements PersonalityTypeService {

	@Autowired
	transient private PersonalityTypeDao dao;

	protected void setDao(final PersonalityTypeDao dao) {
		this.dao = dao;
	}

	@Override
	protected PersonalityTypeDao getDao() {
		return dao;
	}
}
