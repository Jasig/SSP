package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonEducationPlan service
 */
public interface PersonEducationPlanService extends
		AuditableCrudService<PersonEducationPlan> {

	@Override
	PagingWrapper<PersonEducationPlan> getAll(SortingAndPaging sAndP);

	@Override
	PersonEducationPlan get(UUID id) throws ObjectNotFoundException;

	PersonEducationPlan forPerson(Person person);

	@Override
	PersonEducationPlan create(PersonEducationPlan obj);

	@Override
	PersonEducationPlan save(PersonEducationPlan obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}