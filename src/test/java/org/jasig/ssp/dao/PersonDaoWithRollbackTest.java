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

import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
/**
 * For tests of {@link PersonDao} which require rollback
 */
public class PersonDaoWithRollbackTest {

	@Autowired
	private transient PersonDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAllAssignedCoachesWithSimplePaging() throws ObjectNotFoundException {
		Person jamesDoe =  dao.get(Stubs.PersonFixture.JAMES_DOE.id());
		Person kevinSmith =  dao.get(Stubs.PersonFixture.KEVIN_SMITH.id());
		jamesDoe.setCoach(kevinSmith);
		saveAndFlush(jamesDoe);

		Person advisor0 = dao.get(Stubs.PersonFixture.ADVISOR_0.id());
		PagingWrapper<Person> results = dao.getAllAssignedCoaches(
				new SortingAndPaging(ObjectStatus.ALL, 0, 10, null, null, null));
		Iterator<Person> resultIterator = results.getRows().iterator();
		assertEquals(kevinSmith, resultIterator.next());
		assertEquals(advisor0, resultIterator.next());
		assertEquals(3, results.getResults());
	}

	private void saveAndFlush(Person... persons) throws ObjectNotFoundException {
		for ( Person person : persons ) {
			dao.save(person);
		}
		sessionFactory.getCurrentSession().flush();
	}
}
