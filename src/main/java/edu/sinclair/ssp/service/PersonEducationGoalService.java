package edu.sinclair.ssp.service;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationGoal;

public interface PersonEducationGoalService extends
		AuditableCrudService<PersonEducationGoal> {

	@Override
	public List<PersonEducationGoal> getAll(ObjectStatus status,
			int firstResult, int maxResults, String sortExpression);

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
