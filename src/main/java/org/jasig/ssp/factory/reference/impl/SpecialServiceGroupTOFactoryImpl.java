package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.SpecialServiceGroupDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.SpecialServiceGroupTOFactory;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.reference.SpecialServiceGroupTO;

@Service
@Transactional(readOnly = true)
public class SpecialServiceGroupTOFactoryImpl extends
		AbstractReferenceTOFactory<SpecialServiceGroupTO, SpecialServiceGroup>
		implements SpecialServiceGroupTOFactory {

	public SpecialServiceGroupTOFactoryImpl() {
		super(SpecialServiceGroupTO.class, SpecialServiceGroup.class);
	}

	@Autowired
	private transient SpecialServiceGroupDao dao;

	@Override
	protected SpecialServiceGroupDao getDao() {
		return dao;
	}

}
