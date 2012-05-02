package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonEducationPlanService extends
		AuditableCrudService<PersonEducationPlan> {

	@Override
	public PagingWrapper<PersonEducationPlan> getAll(SortingAndPaging sAndP);

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
