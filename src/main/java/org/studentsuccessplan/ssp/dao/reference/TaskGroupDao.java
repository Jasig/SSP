package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.TaskGroup;

/**
 * Data access class for the TaskGroup reference entity.
 */
@Repository
public class TaskGroupDao extends
		ReferenceAuditableCrudDao<TaskGroup>
		implements AuditableCrudDao<TaskGroup> {

	public TaskGroupDao() {
		super(TaskGroup.class);
	}
}
