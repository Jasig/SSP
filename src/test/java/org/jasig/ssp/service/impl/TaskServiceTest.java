/**
 * 
 */
package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.TaskTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class TaskServiceTest {

	@Autowired
	private transient TaskService service;

	private static final UUID CONFIDENTIALITYLEVEL_ID = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Checks that the service calculates the isCompleted property correctly
	 * based on the completion status.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 */
	@Test
	public void testCompletedCalculation() throws ObjectNotFoundException {
		// arrange
		final Task completedTask = createTask();
		final Task incompleteTask = createTask();

		// act
		service.markTaskComplete(completedTask);
		service.markTaskIncomplete(incompleteTask);

		final TaskTO completedTaskTO = new TaskTO(completedTask);
		final TaskTO incompleteTaskTO = new TaskTO(incompleteTask);

		// assert
		assertNotNull("Task completedDate should not have been null.",
				completedTaskTO.getCompletedDate());
		assertTrue(
				"Task generated complete value should have been true.",
				completedTaskTO.isCompleted());

		assertNull("Task completedDate should have been null.",
				incompleteTaskTO.getCompletedDate());
		assertFalse(
				"Task generated complete value should have been false.",
				incompleteTaskTO.isCompleted());
	}

	/**
	 * Checks that the TO changes the completedDate correctly when completed set
	 * instead of the date.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 */
	@Test
	public void testCompletedOverwrites() throws ObjectNotFoundException {
		// arrange
		final Task completedTask = createTask();
		final Task incompleteTask = createTask();

		// act
		service.markTaskComplete(completedTask);
		service.markTaskIncomplete(incompleteTask);

		final TaskTO wasCompletedTaskTO = new TaskTO(completedTask);
		final TaskTO wasIncompleteTaskTO = new TaskTO(incompleteTask);

		// overwrite original completedDate values via the helper setter
		wasCompletedTaskTO.setCompleted(false);
		wasIncompleteTaskTO.setCompleted(true);

		// assert
		assertNull("Task completedDate should have been null.",
				wasCompletedTaskTO.getCompletedDate());
		assertFalse(
				"Task generated complete value should have been false.",
				wasCompletedTaskTO.isCompleted());

		assertNotNull("Task completedDate should not have been null.",
				wasIncompleteTaskTO.getCompletedDate());
		assertTrue(
				"Task generated complete value should have been true.",
				wasIncompleteTaskTO.isCompleted());
	}

	private Task createTask() throws ObjectNotFoundException {
		final Task obj = new Task();
		obj.setName("name");
		obj.setDescription("description");
		obj.setPerson(personService.personFromUsername("ken"));
		obj.setConfidentialityLevel(confidentialityLevelService
				.get(CONFIDENTIALITYLEVEL_ID));
		return obj;
	}
}