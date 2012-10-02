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
package org.jasig.ssp.service.reference.impl;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.AbstractReferenceAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base reference model service methods
 * 
 * @param <T>
 *            Model type
 */
@Transactional
public abstract class AbstractReferenceService<T extends Auditable>
		extends AbstractAuditableCrudService<T>
		implements ReferenceService<T> {

	@Override
	protected abstract AbstractReferenceAuditableCrudDao<T> getDao();

	@Override
	public T save(final T obj) throws ObjectNotFoundException,
			ValidationException {
		try {
			return getDao().save(obj);
		} catch (final ConstraintViolationException exc) {
			throw new ValidationException("Invalid data.", exc);
		}
	}

	@Override
	public T getByName(@NotNull final String name)
			throws ObjectNotFoundException {
		return getDao().getByName(name);
	}
}