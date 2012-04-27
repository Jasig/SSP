package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.CitizenshipDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.CitizenshipTOFactory;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.transferobject.reference.CitizenshipTO;

@Service
@Transactional(readOnly = true)
public class CitizenshipTOFactoryImpl extends
		AbstractReferenceTOFactory<CitizenshipTO, Citizenship>
		implements CitizenshipTOFactory {

	public CitizenshipTOFactoryImpl() {
		super(CitizenshipTO.class, Citizenship.class);
	}

	@Autowired
	private CitizenshipDao dao;

	@Override
	protected CitizenshipDao getDao() {
		return dao;
	}

}
