package org.jasig.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.AbstractRestrictedPersonAssocAuditableService;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Goal service implementation
 */
@Service
@Transactional
public class GoalServiceImpl
		extends AbstractRestrictedPersonAssocAuditableService<Goal>
		implements GoalService {

	@Autowired
	transient private GoalDao dao;

	@Override
	protected GoalDao getDao() {
		return dao;
	}

	@Override
	public List<Goal> getGoalsForPersonIfNoneSelected(
			final List<UUID> selectedIds, final Person person,
			final SspUser requester, final String sessionId,
			final SortingAndPaging sAndP) {
		if (selectedIds == null || selectedIds.isEmpty()) {
			return (List<Goal>) getAllForPerson(person, requester, sAndP)
					.getRows();
		}

		return get(selectedIds, requester, sAndP);
	}
}