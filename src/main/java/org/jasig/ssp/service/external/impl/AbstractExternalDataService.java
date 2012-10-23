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
package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.service.external.ExternalDataService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class which provides a building block for creating an external data
 * service.
 * 
 * @param <T>
 *            Any external data model class.
 */
@Transactional
public abstract class AbstractExternalDataService<T> implements
		ExternalDataService<T> {

	/**
	 * Need access to the data access instance, so make children provide it.
	 * 
	 * @return the DAO
	 */
	protected abstract ExternalDataDao<T> getDao();

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return getDao().getAll(sAndP);
	}
}