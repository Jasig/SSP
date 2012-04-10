package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.EducationGoal;

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
