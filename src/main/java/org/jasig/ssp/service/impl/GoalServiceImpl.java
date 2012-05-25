package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Goal service implementation
 */
@Service
@Transactional
public class GoalServiceImpl
		extends AbstractPersonAssocAuditableService<Goal>
		implements GoalService {

	@Autowired
	transient private GoalDao dao;

	@Override
	protected GoalDao getDao() {
		return dao;
	}
}