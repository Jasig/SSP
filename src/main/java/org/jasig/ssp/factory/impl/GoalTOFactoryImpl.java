package org.jasig.ssp.factory.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.factory.GoalTOFactory;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.GoalTO;

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
