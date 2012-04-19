package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface GoalService extends AuditableCrudService<Goal> {

	@Override
	public List<Goal> getAll(SortingAndPaging sAndP);

	@Override
	public Goal get(UUID id) throws ObjectNotFoundException;

	@Override
	public Goal create(Goal obj);

	@Override
	public Goal save(Goal obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
