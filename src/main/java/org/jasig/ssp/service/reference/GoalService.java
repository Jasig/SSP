package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Goal;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface GoalService extends AuditableCrudService<Goal> {

	@Override
	PagingWrapper<Goal> getAll(SortingAndPaging sAndP);

	@Override
	Goal get(UUID id) throws ObjectNotFoundException;

	@Override
	Goal create(Goal obj) throws ObjectNotFoundException, ValidationException;

	@Override
	Goal save(Goal obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}