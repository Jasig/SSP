package org.jasig.ssp.dao.tool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonToolDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonToolDaoTest.class);

	@Autowired
	private transient PersonToolDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		PersonTool obj = new PersonTool();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setTool(Tools.INTAKE);
		obj.setPerson(securityService.currentUser().getPerson());
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getTool());

		final List<PersonTool> all = (List<PersonTool>) dao.getAll(
				ObjectStatus.ACTIVE).getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final PersonTool personTool = dao.get(id);

		assertNull(personTool);
	}

	private void assertList(final Collection<PersonTool> objects) {
		for (final PersonTool object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void getAllForPersonId() {
		assertList(dao.getAllForPersonId(UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	@Test
	public void getAllForPersonAndTool() {
		assertList(dao.getAllForPersonAndTool(UUID.randomUUID(),
				Tools.ACTION_PLAN,
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}
}
