package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.EducationGoalDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.EducationGoalTOFactory;
import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.transferobject.reference.EducationGoalTO;

@Service
@Transactional(readOnly = true)
public class EducationGoalTOFactoryImpl extends
		AbstractReferenceTOFactory<EducationGoalTO, EducationGoal>
		implements EducationGoalTOFactory {

	public EducationGoalTOFactoryImpl() {
		super(EducationGoalTO.class, EducationGoal.class);
	}

	@Autowired
	private transient EducationGoalDao dao;

	@Override
	protected EducationGoalDao getDao() {
		return dao;
	}

}
