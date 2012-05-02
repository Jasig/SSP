package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.Goal;

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
