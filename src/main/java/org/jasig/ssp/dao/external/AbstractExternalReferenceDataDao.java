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
package org.jasig.ssp.dao.external;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalProgram;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Abstract ExternalData DAO
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Any <code>ExternalData</code> model.
 */
public abstract class AbstractExternalReferenceDataDao<T>
		extends AbstractDao<T>
		implements ExternalDataDao<T> {

	protected AbstractExternalReferenceDataDao(@NotNull final Class<T> persistentClass) {
		super(persistentClass);
	}

	
	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param code
	 *            the <code>{@link Term}.Code</code> value, must be a String
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	@SuppressWarnings("unchecked")
	public T getByCode(@NotNull final String code)
			throws ObjectNotFoundException {
		if (StringUtils.isBlank(code)) {
			throw new ObjectNotFoundException(code, persistentClass.getName());
		}

		final T obj = (T) createCriteria().add(
				Restrictions.eq("code", code)).uniqueResult();

		if (obj == null) {
			throw new ObjectNotFoundException(code, persistentClass.getName());
		}

		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Criteria criteria = createCriteria();
		criteria.addOrder(Order.asc("code"));
		List<T> result = criteria.list();
		return result;
	}
	

	@Override
	@Deprecated
	public PagingWrapper<T> getAll(SortingAndPaging sAndP) {
		throw new NotImplementedException("Use getAll()");
	}

}