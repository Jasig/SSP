package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonStaffDetails service
 */
public interface PersonStaffDetailsService extends
		AuditableCrudService<PersonStaffDetails> {

	@Override
	PagingWrapper<PersonStaffDetails> getAll(SortingAndPaging sAndP);

	@Override
	PersonStaffDetails get(UUID id) throws ObjectNotFoundException;

	PersonStaffDetails forPerson(Person person);

	@Override
	PersonStaffDetails create(PersonStaffDetails obj);

	@Override
	PersonStaffDetails save(PersonStaffDetails obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}