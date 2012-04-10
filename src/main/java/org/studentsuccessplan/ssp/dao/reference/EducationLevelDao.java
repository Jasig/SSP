package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;

/**
 * Data access class for the EducationLevel reference entity.
 */
@Repository
public class EducationLevelDao extends
		ReferenceAuditableCrudDao<EducationLevel>
		implements AuditableCrudDao<EducationLevel> {

	public EducationLevelDao() {
		super(EducationLevel.class);
	}
}
