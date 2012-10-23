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

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.ObjectStatus;
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
public abstract class AbstractExternalDataDao<T>
		extends AbstractDao<T>
		implements ExternalDataDao<T> {

	protected AbstractExternalDataDao(@NotNull final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		return processCriteriaWithSortingAndPaging(createCriteria(),
				new SortingAndPaging(status), false);
	}

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(createCriteria(),
				sAndP, false);
	}
}