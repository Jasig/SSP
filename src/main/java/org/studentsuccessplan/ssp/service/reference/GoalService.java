package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface GoalService extends AuditableCrudService<Goal> {

	@Override
	public List<Goal> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public Goal get(UUID id) throws ObjectNotFoundException;

	@Override
	public Goal create(Goal obj);

	@Override
	public Goal save(Goal obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
