package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
	List<T> getAll(ObjectStatus status);

	/**
	 * Return all entities in the database, filtered only by the specified
	 * parameters.
	 * 
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	List<T> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified instance from persistent storage, or returns null
	 * if not found.
	 * 
	 * @param id
	 * @return The specified instance if found; null otherwise.
	 */
	T get(UUID id);

	/**
	 * Lazily retrieves the specified instance from persistent storage.
	 * Exception will be thrown later, on usage, if any, if the object is not
	 * found.
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
