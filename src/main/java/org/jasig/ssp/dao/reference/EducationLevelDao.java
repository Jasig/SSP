package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.EducationLevel;

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
