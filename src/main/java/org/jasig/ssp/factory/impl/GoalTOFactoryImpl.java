package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.GoalTOFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.GoalTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoalTOFactoryImpl
		extends AbstractAuditableTOFactory<GoalTO, Goal>
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

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());

		if ((tObject.getConfidentialityLevel() == null)
				|| (tObject.getConfidentialityLevel().getId() == null)) {
			model.setConfidentialityLevel(null);
		} else {
			model.setConfidentialityLevel(confidentialityLevelService
					.get(tObject.getConfidentialityLevel().getId()));
		}

		return model;
	}
}
