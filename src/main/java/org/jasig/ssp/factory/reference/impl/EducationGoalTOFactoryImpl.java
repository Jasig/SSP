package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.EducationGoalDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.EducationGoalTOFactory;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.transferobject.reference.EducationGoalTO;

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
