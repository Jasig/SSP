package org.studentsuccessplan.ssp.service;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationGoal;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface PersonEducationGoalService extends
		AuditableCrudService<PersonEducationGoal> {

	@Override
	public PagingWrapper<PersonEducationGoal> getAll(SortingAndPaging sAndP);

	@Override
	public PersonEducationGoal get(UUID id) throws ObjectNotFoundException;

	public PersonEducationGoal forPerson(Person person);

	@Override
	public PersonEducationGoal create(PersonEducationGoal obj);

	@Override
	public PersonEducationGoal save(PersonEducationGoal obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
