package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.MaritalStatusDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.MaritalStatusTOFactory;
import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.transferobject.reference.MaritalStatusTO;

@Service
@Transactional(readOnly = true)
public class MaritalStatusTOFactoryImpl extends
		AbstractReferenceTOFactory<MaritalStatusTO, MaritalStatus>
		implements MaritalStatusTOFactory {

	public MaritalStatusTOFactoryImpl() {
		super(MaritalStatusTO.class, MaritalStatus.class);
	}

	@Autowired
	private transient MaritalStatusDao dao;

	@Override
	protected MaritalStatusDao getDao() {
		return dao;
	}

}
