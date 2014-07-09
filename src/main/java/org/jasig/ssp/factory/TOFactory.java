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
package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.TransferObject;

/**
 * Transfer object factory for converting to and from models.
 * 
 * @author daniel.bower
 * 
 * @param <TObject>
 *            Transfer object type for the corresponding model
 * @param <M>
 *            Model type
 */
public interface TOFactory<TObject extends TransferObject<M>, M> {

	/**
	 * Copy the model to a new transfer object.
	 * 
	 * @param model
	 *            Model to copy
	 * @return Transfer object equivalent of the specified model
	 */
	TObject from(M model);

	/**
	 * Copy a transfer object to a new model.
	 * 
	 * @param tObject
	 *            Transfer object to copy
	 * @return Model equivalent of the specified transfer object
	 * @throws ObjectNotFoundException
	 *             If any referenced objects could not be loaded from persistent
	 *             storage
	 */
	M from(TObject tObject) throws ObjectNotFoundException;

	/**
	 * Load model from storage based on the specified identifier.
	 * 
	 * @param id
	 *            Load model for this identifier
	 * 
	 * @return Model loaded from persistent storage
	 * @throws ObjectNotFoundException
	 */
	M from(UUID id) throws ObjectNotFoundException;

	/**
	 * Copy the list of models to a new list of transfer objects.
	 * 
	 * @param models
	 *            Models to copy
	 * @return Transfer object equivalents of the specified models
	 * @see #asTOSet(Collection)
	 */
	List<TObject> asTOList(Collection<M> models);

	/**
	 * Copy the set of models to a new set of transfer objects.
	 * 
	 * @param models
	 *            Models to copy
	 * @return Transfer object equivalents of the specified models
	 * @see #asTOList(Collection)
	 */
	Set<TObject> asTOSet(Collection<M> models);

	/**
	 * Same as {@link #asTOSet(java.util.Collection)} but preserves
	 * the iteration order of the inbound {@code Collection}.
	 *
	 * @param models
	 *            Models to copy
	 * @return Transfer object equivalents of the specified models,
	 *   preserving original iteration order
	 * @see #asTOSet(Collection)
	 */
	Set<TObject> asTOSetOrdered(Collection<M> models);

	/**
	 * Copy transfer objects to new model instances.
	 * 
	 * @param tObjects
	 *            Transfer objects to copy
	 * @return Model equivalents of the specified transfer objects
	 * @throws ObjectNotFoundException
	 *             If any referenced objects could not be loaded from persistent
	 *             storage
	 */
	Set<M> asSet(Collection<TObject> tObjects)
			throws ObjectNotFoundException;
}
