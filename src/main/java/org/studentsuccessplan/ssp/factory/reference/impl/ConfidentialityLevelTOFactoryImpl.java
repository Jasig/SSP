package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ConfidentialityLevelDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.reference.ConfidentialityLevelTO;

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
