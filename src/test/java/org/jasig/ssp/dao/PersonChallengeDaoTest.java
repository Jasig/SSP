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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests on the {@link PersonChallengeDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonChallengeDaoTest {

	@Autowired
	private transient PersonChallengeDao dao;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private transient Challenge testChallenge;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		testChallenge = challengeService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()
				.iterator().next();
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		// test student = ken thompson
		final Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		final Collection<PersonChallenge> modelsBefore = dao.getAllForPersonId(
				person.getId(), new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();

		// save a new challenge for a person
		final PersonChallenge model = new PersonChallenge(person, testChallenge);
		dao.save(model);

		final Collection<PersonChallenge> modelsAfter = dao.getAllForPersonId(
				person.getId(), new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();

		// we should see more than before
		assertTrue(modelsBefore.size() < modelsAfter.size());

		// makes sure the person is the same
		assertEquals(model.getPerson().getId(), person.getId());

		// fetch the saved one from the db
		final PersonChallenge byId = dao.get(model.getId());
		assertEquals(byId.getId(), model.getId());

		PersonChallenge found = null;
		for (final PersonChallenge pc : person.getChallenges()) {
			if (pc.getId().equals(model.getId())) {
				found = pc;
				break;
			}
		}
		assertNotNull(found);

		// delete it
		person.getChallenges().remove(found);
		dao.delete(model);

		try {
			assertNull(dao.get(model.getId()));
		} catch (final ObjectNotFoundException e) {
			// expected
		}
	}

	@Test
	public void testNull() {
		final UUID id = UUID.randomUUID();
		PersonChallenge model = null;
		try {
			model = dao.get(id);
		} catch (final ObjectNotFoundException e) {
			// expected
		}
		assertNull(model);

		final Collection<PersonChallenge> modelsAfter = dao.getAllForPersonId(
				id, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertEquals(0, modelsAfter.size());
	}

	@Test
	public void testGetAll() {
		dao.getAll(ObjectStatus.ALL);
	}
}