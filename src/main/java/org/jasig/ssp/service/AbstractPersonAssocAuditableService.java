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

import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * Abstract service for Auditable models that are associated with a Person.
 * 
 * @param <T>
 *            An {@link Auditable} model
 */
public abstract class AbstractPersonAssocAuditableService<T extends PersonAssocAuditable>
		extends AbstractAuditableCrudService<T>
		implements PersonAssocAuditableService<T> {

	@Override
	protected abstract PersonAssocAuditableCrudDao<T> getDao();

	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public T save(final T obj) throws ObjectNotFoundException,
			ValidationException {
		return getDao().save(obj);
	}
}