/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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

	void updatePersonFromExternalPerson(final Person person, final ExternalPerson externalPerson, final boolean commit,
										final boolean isStudent);

	/**
	 * Sync external data, if any, into the given Person instance. Will exit
	 * quietly if there is no corresponding external data.
	 *
	 * @param person
	 */
	void updatePersonFromExternalPerson(final Person person, final boolean isStudent);
	
	List<String> getAllDepartmentNames();
}
