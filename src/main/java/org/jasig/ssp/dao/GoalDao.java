package org.jasig.ssp.dao;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.reference.AbstractReferenceAuditableCrudDao;
import org.jasig.ssp.model.Goal;

/**
 * Data access class for the Goal reference entity.
 */
@Repository
public class GoalDao extends
		AbstractReferenceAuditableCrudDao<Goal>
		implements AuditableCrudDao<Goal> {

	public GoalDao() {
		super(Goal.class);
	}
}
