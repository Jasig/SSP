package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.StudentStatus;

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
