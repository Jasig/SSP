package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.RegistrationStatusByTermDao;
import org.jasig.ssp.factory.external.RegistrationStatusByTermTOFactory;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.RegistrationStatusByTermTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RegistrationStatusByTerm transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class RegistrationStatusByTermTOFactoryImpl
		extends
		AbstractExternalDataTOFactory<RegistrationStatusByTermTO, RegistrationStatusByTerm>
		implements RegistrationStatusByTermTOFactory {

	public RegistrationStatusByTermTOFactoryImpl() {
		super(RegistrationStatusByTermTO.class, RegistrationStatusByTerm.class);
	}

	@Autowired
	private transient RegistrationStatusByTermDao dao;

	@Override
	protected RegistrationStatusByTermDao getDao() {
		return dao;
	}

	@Override
	public RegistrationStatusByTerm from(
			final RegistrationStatusByTermTO tObject)
			throws ObjectNotFoundException {

		final RegistrationStatusByTerm model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setTermCode(tObject.getTermCode());
		model.setRegisteredCourseCount(tObject.getRegisteredCourseCount());

		return model;
	}
}
