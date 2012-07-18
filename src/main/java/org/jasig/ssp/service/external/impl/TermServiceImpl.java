package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.TermDao;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Term service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class TermServiceImpl extends AbstractExternalDataService<Term>
		implements TermService {

	@Autowired
	transient private TermDao dao;

	@Override
	protected TermDao getDao() {
		return dao;
	}

	protected void setDao(final TermDao dao) {
		this.dao = dao;
	}

	@Override
	public Term getByCode(final String code) throws ObjectNotFoundException {
		return getDao().getByCode(code);
	}
}