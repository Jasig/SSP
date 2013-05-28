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

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ExternalPersonService extends
		ExternalDataService<ExternalPerson> {

	ExternalPerson getBySchoolId(String schoolId)
			throws ObjectNotFoundException;

	ExternalPerson getByUsername(String username)
			throws ObjectNotFoundException;

	/**
	 * Scheduled Service that synchronizes the Person and ExternalPerson tables
	 */
	void syncWithPerson();

	/**
	 * 
	 *
	 * @param sAndP
	 * @return the total number of person records
	 */
	long syncWithPerson(final SortingAndPaging sAndP) throws InterruptedException;

	void updatePersonFromExternalPerson(final Person person,
			final ExternalPerson externalPerson);
	
	List<String> getAllDepartmentNames();
}
