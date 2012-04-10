package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationPlan;

public interface PersonEducationPlanService extends
		AuditableCrudService<PersonEducationPlan> {

	@Override
	public List<PersonEducationPlan> getAll(ObjectStatus status,
			Integer firstResult, Integer maxResults, String sort,
			String sortDirection);

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
