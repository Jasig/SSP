package edu.sinclair.ssp.web.api;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.transferobject.ServiceResponse;

/**
 * All the Methods a Reference Controller needs to be useful.
 * 
 * @param <T>
 *            The TO type this controller works with.
 */
public abstract class RestController<T> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @exception Exception
	 *                Generic exception thrown if there were any errors.
	 * @return All entities in the database filtered by the supplied status.
	 */
	public abstract List<T> getAll(ObjectStatus status) throws Exception;

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
	 * @exception Exception
	 *                Generic exception thrown if there were any errors.
	 * @return All entities in the database filtered by the supplied status.
	 */
	public abstract List<T> getAll(ObjectStatus status, int firstResult,
			int maxResults, String sortExpression) throws Exception;

	public abstract T get(UUID id) throws Exception;

	public abstract T create(T obj) throws Exception;

	public abstract T save(UUID id, T obj) throws Exception;

	public abstract ServiceResponse delete(UUID id) throws Exception;

	public abstract ServiceResponse handle(Exception e);
}
