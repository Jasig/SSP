package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonEducationGoal service
 */
public interface PersonEducationGoalService extends
		AuditableCrudService<PersonEducationGoal> {

	@Override
	PagingWrapper<PersonEducationGoal> getAll(SortingAndPaging sAndP);

	@Override
	PersonEducationGoal get(UUID id) throws ObjectNotFoundException;

	PersonEducationGoal forPerson(Person person);

	@Override
	PersonEducationGoal create(PersonEducationGoal obj);

	@Override
	PersonEducationGoal save(PersonEducationGoal obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}