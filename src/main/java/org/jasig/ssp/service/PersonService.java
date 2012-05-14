package org.jasig.ssp.service;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface PersonService extends AuditableCrudService<Person> {

	@Override
	PagingWrapper<Person> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified Person.
	 * 
	 * @param id
	 *            Required identifier for the Person to retrieve. Can not be
	 *            null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	@Override
	Person get(UUID id) throws ObjectNotFoundException;

	Person personFromUsername(String username) throws ObjectNotFoundException;

	Person personFromUserId(String userId) throws ObjectNotFoundException;

	/**
	 * Creates a new Person instance based on the supplied model.
	 * 
	 * @param obj
	 *            Model instance
	 */
	@Override
	Person create(Person obj);

	/**
	 * Updates values of direct Person properties, but not any associated
	 * children or collections.
	 * 
	 * WARNING: Copies system-only (based on business logic rules) properties,
	 * so ensure that the incoming values have already been sanitized.
	 * 
	 * @param obj
	 *            Model instance from which to copy the simple properties.
	 * @see IntakeService
	 */
	@Override
	Person save(Person obj) throws ObjectNotFoundException;

	/**
	 * Mark a Person as deleted.
	 * 
	 * Does not remove them from persistent storage, but marks their status flag
	 * to {@link ObjectStatus#DELETED}.
	 */
	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	/**
	 * Return a person object for every personId where available.
	 * 
	 * @param personIds
	 * @param sAndP
	 * @return A person object for every personId where available
	 */
	List<Person> peopleFromListOfIds(List<UUID> personIds,
			SortingAndPaging sAndP);

	/**
	 * Retrieves the specified Person by their Student ID (school_id).
	 * 
	 * @param studentId
	 *            Required school identifier for the Person to retrieve. Can not
	 *            be null.
	 * @exception ObjectNotFoundException
	 *                If the supplied identifier does not exist in the database.
	 * @return The specified Person instance.
	 */
	Person getByStudentId(String studentId) throws ObjectNotFoundException;
}
