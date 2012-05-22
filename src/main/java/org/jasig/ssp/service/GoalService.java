package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Goal;
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