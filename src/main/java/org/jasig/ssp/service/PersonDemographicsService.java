package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonDemographics service
 */
public interface PersonDemographicsService extends
		AuditableCrudService<PersonDemographics> {

	@Override
	PagingWrapper<PersonDemographics> getAll(SortingAndPaging sAndP);

	@Override
	PersonDemographics get(UUID id) throws ObjectNotFoundException;

	PersonDemographics forPerson(Person person);

	@Override
	PersonDemographics create(PersonDemographics obj);

	@Override
	PersonDemographics save(PersonDemographics obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}