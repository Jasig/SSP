package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Goal;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface GoalService extends AuditableCrudService<Goal> {

	@Override
	public PagingWrapper<Goal> getAll(SortingAndPaging sAndP);

	@Override
	public Goal get(UUID id) throws ObjectNotFoundException;

	@Override
	public Goal create(Goal obj);

	@Override
	public Goal save(Goal obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
