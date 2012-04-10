package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonDemographics;

public interface PersonDemographicsService extends
		AuditableCrudService<PersonDemographics> {

	@Override
	public List<PersonDemographics> getAll(ObjectStatus status,
			Integer firstResult, Integer maxResults, String sort,
			String sortDirection);

	@Override
	public PersonDemographics get(UUID id) throws ObjectNotFoundException;

	public PersonDemographics forPerson(Person person);

	@Override
	public PersonDemographics create(PersonDemographics obj);

	@Override
	public PersonDemographics save(PersonDemographics obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
