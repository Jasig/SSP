package org.jasig.ssp.service.impl;

import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Goal service implementation
 */
@Service
@Transactional
public class GoalServiceImpl extends AbstractAuditableCrudService<Goal>
		implements GoalService {

	@Autowired
	transient private GoalDao dao;

	@Override
	protected GoalDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<Goal> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public Goal save(final Goal obj) throws ObjectNotFoundException {
		return getDao().save(obj);
	}
}