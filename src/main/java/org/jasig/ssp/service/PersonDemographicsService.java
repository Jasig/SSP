package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonDemographicsService extends
		AuditableCrudService<PersonDemographics> {

	@Override
	public PagingWrapper<PersonDemographics> getAll(SortingAndPaging sAndP);

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
