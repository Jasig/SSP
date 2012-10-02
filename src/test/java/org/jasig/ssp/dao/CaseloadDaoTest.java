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
package org.jasig.ssp.dao;

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;

import java.util.UUID;

import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for the {@link CaseloadDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class CaseloadDaoTest {

	@Autowired
	private transient CaseloadDao dao;

	@Autowired
	private transient PersonService personService;

	@Test
	public void caseLoadFor() throws ObjectNotFoundException {
		final Person turing = personService.get(UUID
				.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff"));

		final PagingWrapper<CaseloadRecord> caseload = dao.caseLoadFor(null,
				turing, new SortingAndPaging(ObjectStatus.ACTIVE));

		assertNotEmpty("Unable to find any students in caseload",
				caseload.getRows());
	}
}