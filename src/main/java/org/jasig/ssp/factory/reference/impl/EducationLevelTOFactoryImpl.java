package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.EducationLevelDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EducationLevelTOFactory;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.transferobject.reference.EducationLevelTO;

@Service
@Transactional(readOnly = true)
public class EducationLevelTOFactoryImpl extends
		AbstractReferenceTOFactory<EducationLevelTO, EducationLevel>
		implements EducationLevelTOFactory {

	public EducationLevelTOFactoryImpl() {
		super(EducationLevelTO.class, EducationLevel.class);
	}

	@Autowired
	private transient EducationLevelDao dao;

	@Override
	protected EducationLevelDao getDao() {
		return dao;
	}

}
