package org.jasig.ssp.dao;

import org.jasig.ssp.model.Goal;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Goal entity.
 */
@Repository
public class GoalDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<Goal>
		implements RestrictedPersonAssocAuditableDao<Goal> {

	/**
	 * Construct an instance with the specified specific class for use by base
	 * class methods.
	 */
	public GoalDao() {
		super(Goal.class);
	}
}