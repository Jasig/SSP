package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Goal;

/**
 * Data access class for the Goal reference entity.
 */
@Repository
public class GoalDao extends
		ReferenceAuditableCrudDao<Goal>
		implements AuditableCrudDao<Goal> {

	public GoalDao() {
		super(Goal.class);
	}
}
