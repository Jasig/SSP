package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.RegistrationStatusByTermDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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

	@Autowired
	transient private TermService termService;

	@Override
	protected RegistrationStatusByTermDao getDao() {
		return dao;
	}

	protected void setDao(final RegistrationStatusByTermDao dao) {
		this.dao = dao;
	}

	@Override
	public RegistrationStatusByTerm getForCurrentTerm(final Person person)
			throws ObjectNotFoundException {
		return getForTerm(person, termService.getCurrentTerm());
	}

	@Override
	public PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			final Person person, final SortingAndPaging sAndP) {
		return dao.getAllForPerson(person, sAndP);
	}

	@Override
	public RegistrationStatusByTerm getForTerm(final Person person,
			final Term term) {
		return dao.getForTerm(person, term);
	}
}
