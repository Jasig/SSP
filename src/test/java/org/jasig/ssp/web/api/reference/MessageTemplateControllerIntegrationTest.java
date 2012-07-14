package org.jasig.ssp.web.api.reference; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
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

/**
 * {@link MessageTemplateController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class MessageTemplateControllerIntegrationTest {

	@Autowired
	private transient MessageTemplateController controller;

	private static final UUID MESSAGETEMPLATE_ID = MessageTemplate.CONTACT_COACH_ID;

	private static final String MESSAGETEMPLATE_NAME = "Contact Coach Email";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString1";

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link MessageTemplateController#get(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final MessageTemplateTO obj = controller.get(MESSAGETEMPLATE_ID);

		assertNotNull(
				"Returned MessageTemplateTO from the controller should not have been null.",
				obj);

		assertEquals("Returned MessageTemplate.Name did not match.",
				MESSAGETEMPLATE_NAME, obj.getName());
	}

	/**
	 * Test that the {@link MessageTemplateController#get(UUID)} action returns
	 * the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final MessageTemplateTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned MessageTemplateTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link MessageTemplateController#create(MessageTemplateTO)} and
	 * {@link MessageTemplateController#delete(UUID)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		// Check validation of 'no ID for create()'
		try {
			final MessageTemplateTO obj = controller
					.create(new MessageTemplateTO(
							UUID
									.randomUUID(),
							TEST_STRING1, TEST_STRING2)); // NOPMD by
															// jon.adams
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (final ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Now create a valid MessageTemplate
		final MessageTemplateTO toCreate = new MessageTemplateTO(
				null,
				TEST_STRING1,
				TEST_STRING2);
		toCreate.setSubject("a subject");
		toCreate.setBody("my body text");
		final MessageTemplateTO valid = controller.create(toCreate); // NOPMD

		assertNotNull(
				"Returned MessageTemplateTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned MessageTemplateTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned MessageTemplateTO.Name from the controller did not match.",
				TEST_STRING1, valid.getName());
		assertEquals(
				"Returned MessageTemplateTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link MessageTemplateController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<MessageTemplateTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		final Logger logger = controller.getLogger();
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}