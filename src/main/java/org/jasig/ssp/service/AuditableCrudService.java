package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * CRUD services for {@link Auditable} models.
 * 
 * @author daniel.bower
 * 
 * @param <T>
 *            Model type
 */
public interface AuditableCrudService<T extends Auditable> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<T> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 * @return The specified instance if found.
	 * @exception ObjectNotFoundException
	 *                If instance not found or inactive.
	 */
	T get(UUID id) throws ObjectNotFoundException;

	/**
	 * Save a new instance to persistent storage.
	 * 
	 * <p>
	 * Side effect: Assigns a new ID to the specified object.
	 * 
	 * @param obj
	 *            Data to use to create a new instance in persistent storage
	 * @return The updated data object instance
	 * @throws ObjectNotFoundException
	 *             If any foreign key lookups were not found
	 * @throws ValidationException
	 *             If the business object is invalid
	 */
	T create(T obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Updates an existing instance to persistent storage with the specified
	 * values.
	 * 
	 * @param obj
	 *            New data to use to overwrite the existing data, matched by the
	 *            ID.
	 * @return The updated data object instance.
	 * @exception ObjectNotFoundException
	 *                If the specified ID does not already exist, or if any
	 *                foreign key lookups were not found.
	 * @exception ValidationException
	 *                If any validation errors occurred.
	 */
	T save(T obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Mark the specific instance as {@link ObjectStatus#DELETED}.
	 * 
	 * @param id
	 *            Instance identifier
	 * @exception ObjectNotFoundException
	 *                if the specified ID does not exist.
	 */
	void delete(UUID id) throws ObjectNotFoundException;
}