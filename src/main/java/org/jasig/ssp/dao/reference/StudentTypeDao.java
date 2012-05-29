package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.StudentType;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the StudentType reference entity.
 */
@Repository
public class StudentTypeDao extends
		AbstractReferenceAuditableCrudDao<StudentType>
		implements AuditableCrudDao<StudentType> {

	public StudentTypeDao() {
		super(StudentType.class);
	}
}