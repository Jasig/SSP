package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

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
	 * @param status
	 *            Object status filter. Set to {@link ObjectStatus#ALL} to
	 *            return all results.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sort
	 *            Property name. If null or empty string, the default sort will
	 *            be used. If non-empty, must be a case-sensitive model property
	 *            name. Often comes from client as a parameter labeled
	 *            <code>sort</code>. Example sort expression:
	 *            <code>propertyName</code>
	 * @param sortDirection
	 *            Ascending/descending keyword. If null or empty string, the
	 *            default sort will be used. Must be <code>ASC</code> or
	 *            <code>DESC</code>.
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 * @return The specified instance if found.
	 */
	T get(UUID id);

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
