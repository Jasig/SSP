package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;

@Service
@Transactional(readOnly = true)
public class ConfidentialityLevelTOFactoryImpl
		extends
		AbstractReferenceTOFactory<ConfidentialityLevelTO, ConfidentialityLevel>
		implements ConfidentialityLevelTOFactory {

	public ConfidentialityLevelTOFactoryImpl() {
		super(ConfidentialityLevelTO.class, ConfidentialityLevel.class);
	}

	@Autowired
	private transient ConfidentialityLevelDao dao;

	@Override
	protected ConfidentialityLevelDao getDao() {
		return dao;
	}

	@Override
	public ConfidentialityLevel from(final ConfidentialityLevelTO tObject)
			throws ObjectNotFoundException {
		final ConfidentialityLevel model = super.from(tObject);

		model.setAcronym(tObject.getAcronym());

		return model;
	}

}
