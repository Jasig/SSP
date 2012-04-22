package org.studentsuccessplan.ssp.service;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface AuditableCrudService<T> {

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
	 */
	T get(UUID id) throws ObjectNotFoundException;

	/**
	 * Save instance to persistent storage.
	 * 
	 * @param obj
	 * @return The update data object instance.
	 */
	T create(T obj);

	/**
	 * Save instance to persistent storage.
	 * 
	 * @param obj
	 * @return The update data object instance.
	 * @exception ObjectNotFoundException
	 */
	T save(T obj) throws ObjectNotFoundException;

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
