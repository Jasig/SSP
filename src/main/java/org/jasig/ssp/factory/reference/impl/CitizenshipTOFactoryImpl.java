package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.CitizenshipDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.CitizenshipTOFactory;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.transferobject.reference.CitizenshipTO;

@Service
@Transactional(readOnly = true)
public class CitizenshipTOFactoryImpl extends
		AbstractReferenceTOFactory<CitizenshipTO, Citizenship>
		implements CitizenshipTOFactory {

	public CitizenshipTOFactoryImpl() {
		super(CitizenshipTO.class, Citizenship.class);
	}

	@Autowired
	private transient CitizenshipDao dao;

	@Override
	protected CitizenshipDao getDao() {
		return dao;
	}

}
