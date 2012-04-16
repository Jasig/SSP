package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.TaskGroup;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface TaskGroupService extends AuditableCrudService<TaskGroup> {

	@Override
	public List<TaskGroup> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public TaskGroup get(UUID id) throws ObjectNotFoundException;

	@Override
	public TaskGroup create(TaskGroup obj);

	@Override
	public TaskGroup save(TaskGroup obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
