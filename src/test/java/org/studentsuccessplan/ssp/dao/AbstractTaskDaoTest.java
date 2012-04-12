package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@Transactional
public abstract class AbstractTaskDaoTest<T extends AbstractTask> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractTaskDaoTest.class);

	@Autowired
	protected PersonService personService;

	@Autowired
	protected SecurityServiceInTestEnvironment securityService;

	protected abstract AbstractTaskDao<T> getDao();

	protected Person ken;

	public void setUp() {
		try {
			ken = personService.personFromUsername("ken");
		} catch (ObjectNotFoundException e) {
			LOGGER.error("can't find one of either sysadmin or ken");
		}
		securityService.setCurrent(ken);
	}

	@Test
	public void getAllForPersonId() {
		assertList(getDao().getAllForPersonId(ken.getId()));
	}

	@Test
	public void getAllForPersonId_complete() {
		assertList(getDao().getAllForPersonId(ken.getId(), true));
	}

	@Test
	public void getAllForPersonId_incomplete() {
		assertList(getDao().getAllForPersonId(ken.getId(), false));
	}

	@Test
	public void getAllForSessionId() {
		assertList(getDao().getAllForSessionId("test session id"));
	}

	@Test
	public void getAllForSessionId_complete() {
		assertList(getDao().getAllForSessionId("test session id", true));
	}

	@Test
	public void getAllForSessionId_incomplete() {
		assertList(getDao().getAllForSessionId("test session id", false));
	}

	@Test
	public void getAllWhichNeedRemindersSent() {
		assertList(getDao().getAllWhichNeedRemindersSent());
	}

	protected void assertList(List<T> objects) {
		for (T object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}
}
