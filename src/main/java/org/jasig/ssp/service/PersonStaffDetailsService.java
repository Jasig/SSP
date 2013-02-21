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
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.model.ObjectStatus;

/**
 * PersonStaffDetails service
 */
public interface PersonStaffDetailsService extends
		AuditableCrudService<PersonStaffDetails> {

	@Override
	PagingWrapper<PersonStaffDetails> getAll(SortingAndPaging sAndP);

	@Override
	PersonStaffDetails get(UUID id) throws ObjectNotFoundException;

	PersonStaffDetails forPerson(Person person);

	@Override
	PersonStaffDetails create(PersonStaffDetails obj);

	@Override
	PersonStaffDetails save(PersonStaffDetails obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
	
	List<String> getAllHomeDepartments(ObjectStatus status);
}