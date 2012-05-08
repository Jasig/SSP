package org.jasig.ssp.dao;

import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Required interface for all the basic CRUD methods for the DAO layer.
 * 
 * @param <T>
 *            Any Auditable model class
 */
public interface AuditableCrudDao<T extends Auditable> {

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @param status
	 *            Object status filter. Set to {@link ObjectStatus#ALL} to
	 *            return all results.
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	PagingWrapper<T> getAll(ObjectStatus status);

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	PagingWrapper<T> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified instance from persistent storage. DOES NOT check
	 * {@link ObjectStatus}.
	 * 
	 * @param id
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	T get(UUID id) throws ObjectNotFoundException;

	/**
	 * Lazily retrieves the specified instance from persistent storage.
	 * Exception will be thrown later, on usage, if any, if the object is not
	 * found. DOES NOT check for {@link ObjectStatus#ACTIVE} status.
	 * 
	 * @param id
	 * @return The specified instance.
	 */
	T load(UUID id);

	/**
	 * Save instance to persistent storage.
	 * 
	 * @param obj
	 * @return The update data object instance.
	 */
	T save(T obj);

	/**
	 * <i>Permanently</i> removes the specified data instance!
	 * <p>
	 * In the SSP application, not entities should be deleted in a production
	 * system. This method should only be used for testing and debugging
	 * purposes. To mark objects as deleted without removing them, use
	 * {@link Auditable#setObjectStatus(ObjectStatus)} to set a status of
	 * {@link ObjectStatus#DELETED}.
	 * 
	 * @param id
	 *            The id of the data instance to permanently delete.
	 */
	void delete(T id);
}
