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
package org.jasig.ssp.service.impl;

import java.util.Collection;

import org.jasig.ssp.dao.PersonSearchDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * PersonSearch service implementation
 */
@Service
@Transactional
public class PersonSearchServiceImpl implements PersonSearchService {

	@Autowired
	private transient PersonSearchDao dao;

	@Autowired
	private transient PersonProgramStatusService personProgramStatus;

	@Override
	public PagingWrapper<PersonSearchResult> searchBy(
			final ProgramStatus programStatus,
			final Boolean requireProgramStatus,
			final Boolean outsideCaseload,
			final String searchTerm, final Person advisor,
			final SortingAndPaging sAndP)
			throws ObjectNotFoundException, ValidationException {

		final PagingWrapper<Person> people = dao.searchBy(programStatus,
				(requireProgramStatus == null || programStatus != null ? Boolean.TRUE : requireProgramStatus),
				(outsideCaseload == null ? Boolean.FALSE : outsideCaseload),
				searchTerm, advisor, sAndP);

		final Collection<PersonSearchResult> personSearchResults = Lists
				.newArrayList();
		for (final Person person : people) {
			personSearchResults.add(new PersonSearchResult(person, // NOPMD
					personProgramStatus));
		}

		return new PagingWrapper<PersonSearchResult>(people.getResults(),
				personSearchResults);
	}
}