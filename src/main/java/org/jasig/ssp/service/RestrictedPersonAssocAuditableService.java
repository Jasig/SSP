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

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface RestrictedPersonAssocAuditableService<T extends RestrictedPersonAssocAuditable>
		extends PersonAssocAuditableService<T> {

	/**
	 * Retrieves the specified instances from persistent storage.
	 * 
	 * @param ids
	 *            the identifiers of the entities to load
	 * @param requester
	 *            user requesting the data, for restricting by
	 *            {@link ConfidentialityLevel}
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return Specified entities, filtered by the specified parameters, or
	 *         empty List if null
	 */
	List<T> get(final List<UUID> ids, final SspUser requester,
			final SortingAndPaging sAndP);

	PagingWrapper<T> getAllForPerson(Person person,
			SspUser requestor,
			SortingAndPaging sAndP);

	@Override
	@Deprecated
	PagingWrapper<T> getAllForPerson(Person person,
			SortingAndPaging sAndP);
}
