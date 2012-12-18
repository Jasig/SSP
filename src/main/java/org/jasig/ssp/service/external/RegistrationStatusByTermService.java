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

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * RegistrationStatusByTerm service
 */
public interface RegistrationStatusByTermService extends
		ExternalDataService<RegistrationStatusByTerm> {

	RegistrationStatusByTerm getForTerm(@NotNull Person person,
			@NotNull Term term);

	/**
	 * Gets all registration statuses for the current term.
	 * 
	 * @param person
	 *            the person
	 * @return All registration statuses for the current term.
	 * @throws ObjectNotFoundException
	 *             if current term does not exist.
	 */
	RegistrationStatusByTerm getForCurrentTerm(@NotNull Person person)
			throws ObjectNotFoundException;

	PagingWrapper<RegistrationStatusByTerm> getAllForPerson(
			@NotNull final Person person, final SortingAndPaging sAndP);
	
	PagingWrapper<RegistrationStatusByTerm> getAllForTerm(
			@NotNull final Term term, final SortingAndPaging sAndP);

	Person applyRegistrationStatusForCurrentTerm(@NotNull Person person);
}