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
package org.jasig.ssp.service.external;

import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Base class which provides a building block for creating an external data
 * service.
 * 
 * @param <T>
 *            Any external data model class.
 */
public interface ExternalDataService<T> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status,
	 * sorting, and paging parameters.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities filtered by the supplied parameters.
	 */
	PagingWrapper<T> getAll(final SortingAndPaging sAndP);
}