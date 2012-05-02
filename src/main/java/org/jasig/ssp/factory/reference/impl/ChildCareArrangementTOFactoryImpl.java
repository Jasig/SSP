package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ChildCareArrangementDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ChildCareArrangementTOFactory;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.transferobject.reference.ChildCareArrangementTO;

@Service
@Transactional(readOnly = true)
public class ChildCareArrangementTOFactoryImpl
		extends
		AbstractReferenceTOFactory<ChildCareArrangementTO, ChildCareArrangement>
		implements ChildCareArrangementTOFactory {

	public ChildCareArrangementTOFactoryImpl() {
		super(ChildCareArrangementTO.class, ChildCareArrangement.class);
	}

	@Autowired
	private transient ChildCareArrangementDao dao;

	@Override
	protected ChildCareArrangementDao getDao() {
		return dao;
	}

}
