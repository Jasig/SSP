package edu.sinclair.ssp.service;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonEducationPlan;

public interface PersonEducationPlanService extends
		AuditableCrudService<PersonEducationPlan> {

	@Override
	public List<PersonEducationPlan> getAll(ObjectStatus status);

	@Override
	public PersonEducationPlan get(UUID id) throws ObjectNotFoundException;

	public PersonEducationPlan forPerson(Person person);

	@Override
	public PersonEducationPlan create(PersonEducationPlan obj);

	@Override
	public PersonEducationPlan save(PersonEducationPlan obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
