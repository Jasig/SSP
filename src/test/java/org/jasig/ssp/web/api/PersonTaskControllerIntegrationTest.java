package org.jasig.ssp.web.api; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.form.EmailPersonTasksForm;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * {@link PersonTaskController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonTaskControllerIntegrationTest {

	@Autowired
	private transient PersonTaskController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	// Can't use service because it doesn't offer GetAll or similar methods
	@Autowired
	protected transient MessageDao messageDao;

	@Autowired
	protected transient PersonService personService;

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final String TEST_EMAIL = "test@example.com";

	private static final UUID GOAL_ID = UUID
			.fromString("1B18BF52-BFC7-11E1-9CB8-0026B9E7FF4C");

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				"ROLE_PERSON_TASK_READ",
				"ROLE_PERSON_TASK_WRITE",
				"ROLE_PERSON_TASK_DELETE");
	}

	/**
	 * Test the email action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerEmail() throws ObjectNotFoundException,
			ValidationException {
		// arrange
		final List<UUID> recipientIds = Lists.newArrayList();
		recipientIds.add(PERSON_ID);

		final List<String> recipientEmailAddresses = Lists.newArrayList();
		recipientEmailAddresses.add(TEST_EMAIL);

		final List<UUID> taskIds = Lists.newArrayList();

		final List<UUID> goalIds = Lists.newArrayList();
		goalIds.add(GOAL_ID);

		final EmailPersonTasksForm emailForm = new EmailPersonTasksForm();
		emailForm.setRecipientIds(recipientIds);
		emailForm.setRecipientEmailAddresses(recipientEmailAddresses);
		emailForm.setTaskIds(taskIds);
		emailForm.setGoalIds(goalIds);

		// act
		final boolean result = controller.email(PERSON_ID, emailForm);

		// assert
		assertTrue("Send e-mail should have returned success.", result);
	}

	@Test
	public void testLogger() {
		final Logger logger = controller.getLogger();
		logger.info("Test");
		assertNotNull("logger should not have been null.", logger);
		assertEquals("Logger name was not specific to the class.",
				"org.jasig.ssp.web.api.PersonTaskController",
				logger.getName());
	}
}