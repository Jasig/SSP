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
package org.jasig.ssp.factory.reference;

import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;

/**
 * Reference transfer object factory base class.
 * 
 * @param <TObject>
 *            Transfer object class for the associated model class
 * @param <M>
 *            The model class
 */
public abstract class AbstractReferenceTOFactory<TObject extends AbstractReferenceTO<M>, M extends AbstractReference>
		extends AbstractAuditableTOFactory<TObject, M>
		implements TOFactory<TObject, M> {

	/**
	 * Constructor that initializes specific class instances for use by the
	 * common base class methods.
	 * 
	 * @param tObjectClass
	 *            Transfer object class for the associated model class
	 * @param mClass
	 *            The model class
	 */
	public AbstractReferenceTOFactory(final Class<TObject> tObjectClass,
			final Class<M> mClass) {
		super(tObjectClass, mClass);
	}

	@Override
	public M from(final TObject tObject) throws ObjectNotFoundException {
		final M model = super.from(tObject);

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());

		return model;
	}
}