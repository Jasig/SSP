package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationGoal;

public interface PersonEducationGoalService extends
		AuditableCrudService<PersonEducationGoal> {

	@Override
	public List<PersonEducationGoal> getAll(ObjectStatus status,
			Integer firstResult, Integer maxResults, String sort,
			String sortDirection);

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
