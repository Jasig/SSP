package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;

public interface AuditableCrudService<T> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>start</code>. A null value indicates
	 *            to return rows starting from index 0.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>limit</code>. A null value indicates
	 *            return all rows from the start parameter to the end of the
	 *            data.
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
	 * @return All entities in the database filtered by the supplied status.
	 */
	public List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	public T get(UUID id) throws ObjectNotFoundException;

	public T create(T obj);

	public T save(T obj) throws ObjectNotFoundException;

	/**
	 * Mark the specific instance as {@link ObjectStatus#DELETED}.
	 * 
	 * @param id
	 *            Instance identifier
	 * @exception ObjectNotFoundException
	 *                if the specified ID does not exist.
	 */
	public void delete(UUID id) throws ObjectNotFoundException;
}
