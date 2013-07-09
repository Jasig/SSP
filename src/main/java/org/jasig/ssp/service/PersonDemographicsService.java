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

import java.math.BigDecimal;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonDemographics service
 */
public interface PersonDemographicsService extends
		AuditableCrudService<PersonDemographics> {

	@Override
	PagingWrapper<PersonDemographics> getAll(SortingAndPaging sAndP);

	@Override
	PersonDemographics get(UUID id) throws ObjectNotFoundException;

	PersonDemographics forPerson(Person person);

	@Override
	PersonDemographics create(PersonDemographics obj);

	@Override
	PersonDemographics save(PersonDemographics obj)
			throws ObjectNotFoundException;

	BigDecimal getBalancedOwed(UUID personId);
	
	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}