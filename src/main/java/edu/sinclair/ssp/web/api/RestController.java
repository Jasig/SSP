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
	 * @param start
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>start</code>. A null value indicates
	 *            to return rows starting from index 0.
	 * @param limit
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
	 * @exception Exception
	 *                Generic exception thrown if there were any errors.
	 * @return All entities in the database filtered by the supplied status.
	 */
	public abstract List<T> getAll(ObjectStatus status, Integer start,
			Integer limit, String sort, String sortDirection) throws Exception;

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 * @return The specified instance if found.
	 * @throws Exception
	 */
	public abstract T get(UUID id) throws Exception;

	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 * 
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws edu.sinclair.ssp.web.api.validation.ValidationException
	 *             If the obj contains an id (since it shouldn't).
	 * @throws Exception
	 */
	public abstract T create(T obj) throws Exception;

	/**
	 * Persist any changes to the specified instance.
	 * 
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 * @return The update data object instance.
	 * @throws edu.sinclair.ssp.web.api.validation.ValidationException
	 *             If the specified id is null.
	 * @throws Exception
	 */
	public abstract T save(UUID id, T obj) throws Exception;

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#DELETED}.
	 * 
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws Exception
	 */
	public abstract ServiceResponse delete(UUID id) throws Exception;

	/**
	 * Handles any exception thrown by, or allowed to propagate up through the
	 * controller.
	 * 
	 * @param e
	 * @return Message containing the reason for the exception.
	 */
	public abstract ServiceResponse handle(Exception e);
}
