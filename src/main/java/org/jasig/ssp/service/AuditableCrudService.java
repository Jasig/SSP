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
package org.jasig.ssp.service;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * CRUD services for {@link Auditable} models.
 * 
 * @author daniel.bower
 * 
 * @param <T>
 *            Model type
 */
public interface AuditableCrudService<T extends Auditable> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities in the database filtered by the supplied status,
	 *         sorting, and paging parameters.
	 */
	PagingWrapper<T> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 *            the id; required
	 * @return The specified instance if found and active,
	 *         ObjectNotFoundException thrown otherwise.
	 * @exception ObjectNotFoundException
	 *                If instance not found or inactive.
	 */
	T get(@NotNull UUID id) throws ObjectNotFoundException;

	/**
	 * Save a new instance to persistent storage.
	 * 
	 * <p>
	 * Side effect: Assigns a new ID to the specified object.
	 *
	 * @deprecated use TO-based creation method(s) instead, if present.
	 * @param obj
	 *            Data to use to create a new instance in persistent storage
	 * @return The updated data object instance
	 * @throws ObjectNotFoundException
	 *             If any foreign key lookups were not found
	 * @throws ValidationException
	 *             If the business object is invalid
	 */
	T create(T obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Updates an existing instance to persistent storage with the specified
	 * values.
	 * 
	 * @param obj
	 *            New data to use to overwrite the existing data, matched by the
	 *            ID.
	 * @return The updated data object instance.
	 * @exception ObjectNotFoundException
	 *                If the specified ID does not already exist, or if any
	 *                foreign key lookups were not found.
	 * @exception ValidationException
	 *                If any validation errors occurred.
	 */
	T save(T obj) throws ObjectNotFoundException, ValidationException;

	/**
	 * Mark the specific instance as {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            Instance identifier
	 * @exception ObjectNotFoundException
	 *                if the specified ID does not exist.
	 */
	void delete(UUID id) throws ObjectNotFoundException;

}