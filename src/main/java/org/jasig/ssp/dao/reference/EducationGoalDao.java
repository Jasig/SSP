package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.EducationGoal;

/**
 * Data access class for the EducationGoal reference entity.
 */
@Repository
public class EducationGoalDao extends ReferenceAuditableCrudDao<EducationGoal>
		implements AuditableCrudDao<EducationGoal> {

	public EducationGoalDao() {
		super(EducationGoal.class);
	}
}
