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
package org.jasig.ssp.service.impl; // NOPMD

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class GoalServiceTest {

	private static final UUID TEST_GOAL_ID1 = UUID
			.fromString("1B18BF52-BFC7-11E1-9CB8-0026B9E7FF4C");

	@Autowired
	private transient GoalService service;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 * 
	 * @throws ObjectNotFoundException
	 *             If test user could not be found.
	 */
	@Before
	public void setUp() throws ObjectNotFoundException {
		securityService.setCurrent(personService.personFromUsername("ken"),
				confidentialityLevelService
						.confidentialityLevelsAsGrantedAuthorities());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithEmptyList() {
		service.get(new ArrayList<UUID>(), securityService.currentUser(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithNullList() {
		service.get(null, securityService.currentUser(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithNullUser() {
		// arrange
		final List<UUID> list = Lists.newArrayList();
		list.add(TEST_GOAL_ID1);

		// act
		service.get(list, null, new SortingAndPaging(ObjectStatus.ALL));
	}

	@Test
	public void testGetList() {
		// arrange
		final List<UUID> list = Lists.newArrayList();
		list.add(TEST_GOAL_ID1);

		// act
		final List<Goal> result = service.get(list, securityService
				.currentUser(), new SortingAndPaging(ObjectStatus.ALL));

		// assert
		assertEquals(
				"Result list did not contain the expected number of items.", 1,
				result.size());
	}
}