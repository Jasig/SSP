package org.studentsuccessplan.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.GoalDao;
import org.studentsuccessplan.ssp.factory.reference.AbstractReferenceTOFactory;
import org.studentsuccessplan.ssp.factory.reference.GoalTOFactory;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityLevelService;
import org.studentsuccessplan.ssp.transferobject.reference.GoalTO;

@Service
@Transactional(readOnly = true)
public class GoalTOFactoryImpl extends
		AbstractReferenceTOFactory<GoalTO, Goal>
		implements GoalTOFactory {

	public GoalTOFactoryImpl() {
		super(GoalTO.class, Goal.class);
	}

	@Autowired
	private transient GoalDao dao;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Override
	protected GoalDao getDao() {
		return dao;
	}

	@Override
	public Goal from(final GoalTO tObject) throws ObjectNotFoundException {
		final Goal model = super.from(tObject);

		model.setConfidentialityLevel(confidentialityLevelService.get(tObject
				.getConfidentialityLevelId()));

		return model;
	}
}
