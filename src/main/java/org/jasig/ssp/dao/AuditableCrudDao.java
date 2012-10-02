/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Required interface for all the basic CRUD methods for the DAO layer.
 * 
 * @param <T>
 *            Any {@link Auditable} model class
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
	 * Retrieves the specified instances from persistent storage.
	 * 
	 * @param ids
	 *            the identifiers to load; required to be non-empty
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return Specified entities, filtered by the specified parameters, or
	 *         empty List if null
	 */
	PagingWrapper<T> get(@NotNull List<UUID> ids,
			final SortingAndPaging sAndP);

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
	 * In the SSP application, no entities should be deleted in a production
	 * system. This method should only be used for testing and debugging
	 * purposes. To mark objects as deleted without removing them, use
	 * {@link Auditable#setObjectStatus(ObjectStatus)} to set a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            The data instance to permanently delete.
	 */
	void delete(T id);
}