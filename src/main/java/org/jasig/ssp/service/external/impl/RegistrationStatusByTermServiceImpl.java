package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.RegistrationStatusByTermDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RegistrationStatusByTerm service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class RegistrationStatusByTermServiceImpl extends
		AbstractExternalDataService<RegistrationStatusByTerm>
		implements RegistrationStatusByTermService {

	@Autowired
	transient private RegistrationStatusByTermDao dao;

	@Override
	protected RegistrationStatusByTermDao getDao() {
		return dao;
	}

	protected void setDao(final RegistrationStatusByTermDao dao) {
		this.dao = dao;
	}

	@Override
	public RegistrationStatusByTerm registeredForCurrentTerm(
			final Person person) {
		return dao.registeredForCurrentTerm(person);
	}
}
