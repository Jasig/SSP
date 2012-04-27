package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.MaritalStatusDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.MaritalStatusTOFactory;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.transferobject.reference.MaritalStatusTO;

@Service
@Transactional(readOnly = true)
public class MaritalStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<MaritalStatusTO, MaritalStatus>
		implements MaritalStatusTOFactory {

	public MaritalStatusTOFactoryImpl() {
		super(MaritalStatusTO.class, MaritalStatus.class);
	}

	@Autowired
	private MaritalStatusDao dao;

	@Override
	protected MaritalStatusDao getDao() {
		return dao;
	}

}
