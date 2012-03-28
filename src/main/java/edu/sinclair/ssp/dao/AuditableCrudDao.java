package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;

public interface AuditableCrudDao<T> {

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
	public List<T> getAll(ObjectStatus status);

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
	 * @param sortExpression
	 *            Property name and ascending/descending keyword. If null or
	 *            empty string, the default sort order will be used. Example
	 *            sort expression: <code>propertyName ASC</code>
	 * @return All entities in the database, filtered only by the specified
	 *         parameters.
	 */
	public List<T> getAll(ObjectStatus status, int firstResult, int maxResults,
			String sortExpression);

	public T get(UUID id);

	public T save(T obj);

	public void delete(T id);
}
