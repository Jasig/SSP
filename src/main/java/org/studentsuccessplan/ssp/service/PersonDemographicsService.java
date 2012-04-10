package edu.sinclair.ssp.service;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;

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
