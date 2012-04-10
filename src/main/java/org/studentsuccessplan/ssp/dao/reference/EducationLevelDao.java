package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.EducationLevel;

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
