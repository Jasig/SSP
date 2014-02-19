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

import org.jasig.ssp.model.Strength;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Strength service
 */
public interface StrengthService extends
		RestrictedPersonAssocAuditableService<Strength> {

	/**
	 * If tasks are selected, get them, otherwise return the tasks for the
	 * person, (just for the session if it is the anonymous user).
	 * 
	 * @param selectedIds
	 *            Selected {@link Task} identifiers
	 * @param person
	 *            the person
	 * @param requester
	 *            the requester
	 * @param sessionId
	 *            session identifier
	 * @param sAndP
	 *            sorting and paging options
	 * @return Selected tasks, or all tasks for the anonymous user.
	 */
	List<Strength> getStrengthsForPersonIfNoneSelected(final List<UUID> selectedIds,
			final Person person, final SspUser requester,
			final String sessionId, final SortingAndPaging sAndP);
}