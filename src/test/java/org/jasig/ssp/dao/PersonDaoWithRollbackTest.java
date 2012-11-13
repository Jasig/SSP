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
		assertEquals(2, results.getResults());
	}

	private void saveAndFlush(Person... persons) throws ObjectNotFoundException {
		for ( Person person : persons ) {
			dao.save(person);
		}
		sessionFactory.getCurrentSession().flush();
	}
}
