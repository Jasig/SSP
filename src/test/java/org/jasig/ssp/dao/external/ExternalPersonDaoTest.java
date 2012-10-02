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
package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ExternalPersonDaoTest {

	@Autowired
	private transient ExternalPersonDao dao;

	@SuppressWarnings("null")
	@Test
	public void getBySchoolId() {
		ExternalPerson person = null;
		try {
			person = dao.getBySchoolId("notInSsp");
		} catch (final ObjectNotFoundException e) {
			fail("external User not found");
		}

		assertNotNull("Person was not found", person);
		assertEquals("Incorrect school id", "notInSsp", person.getSchoolId());
		assertEquals("Incorrect coach", "turing.1", person.getCoachSchoolId());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBySchoolIdThrowException() throws ObjectNotFoundException {
		dao.getBySchoolId("borkborkbork");
	}

	@Test
	public void getBySchoolIds() {
		final List<String> schoolIds = Lists.newArrayList();
		schoolIds.add("ken.1");
		schoolIds.add("turing.1");

		final PagingWrapper<ExternalPerson> diff = dao
				.getBySchoolIds(schoolIds, new SortingAndPaging(
						ObjectStatus.ACTIVE));

		assertEquals("Incorrect number of external_user entries", 2L,
				diff.getResults());
	}
}
