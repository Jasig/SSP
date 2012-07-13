package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.TermDao;
import org.jasig.ssp.factory.external.TermTOFactory;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.TermTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TermTOFactoryImpl
		extends AbstractExternalDataTOFactory<Term, TermTO>
		implements TermTOFactory {

	public TermTOFactoryImpl() {
		super(TermTO.class, Term.class);
	}

	@Autowired
	private transient TermDao dao;

	@Override
	protected TermDao getDao() {
		return dao;
	}

	@Override
	public Term from(final TermTO tObject) throws ObjectNotFoundException {
		final Term model = super.from(tObject);

		model.setCode(tObject.getCode());
		model.setStartDate(tObject.getStartDate());
		model.setEndDate(tObject.getEndDate());
		model.setReportYear(tObject.getReportYear());

		return model;
	}
}
