package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.StudentStatus;

/**
 * Data access class for the StudentStatus reference entity.
 */
@Repository
public class StudentStatusDao extends ReferenceAuditableCrudDao<StudentStatus>
		implements AuditableCrudDao<StudentStatus> {

	public StudentStatusDao() {
		super(StudentStatus.class);
	}
}
