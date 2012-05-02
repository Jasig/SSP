package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;

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
