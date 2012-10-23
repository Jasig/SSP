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

import java.util.UUID;

import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertRouting service
 * 
 * @author jon.adams
 */
public interface EarlyAlertRoutingService extends
		AuditableCrudService<EarlyAlertRouting> {
	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param campus
	 *            Only show data for this campus
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<EarlyAlertRouting> getAllForCampus(Campus campus,
			SortingAndPaging sAndP);

	/**
	 * Ensure the campusId matches the embedded campusId in the specified
	 * {@link EarlyAlertRouting} model.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param obj
	 *            EarlyAlertRouting model with embedded Campus data
	 * @throws ObjectNotFoundException
	 *             If Campus could not be found
	 */
	void checkCampusIds(final UUID campusId, final EarlyAlertRouting obj)
			throws ObjectNotFoundException;
}