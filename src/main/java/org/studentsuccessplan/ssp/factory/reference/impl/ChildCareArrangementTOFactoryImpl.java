package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChildCareArrangementDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ChildCareArrangementTOFactory;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;

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
	private ChildCareArrangementDao dao;

	@Override
	protected ChildCareArrangementDao getDao() {
		return dao;
	}

}
