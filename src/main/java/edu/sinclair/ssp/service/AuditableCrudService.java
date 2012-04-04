package edu.sinclair.ssp.service;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;

public interface AuditableCrudService<T> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @param firstResult
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param maxResults
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer.
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database filtered by the supplied status.
	 */
	public List<T> getAll(ObjectStatus status, int firstResult, int maxResults,
			String sortExpression);

	public T get(UUID id) throws ObjectNotFoundException;

	public T create(T obj);

	public T save(T obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;
}
