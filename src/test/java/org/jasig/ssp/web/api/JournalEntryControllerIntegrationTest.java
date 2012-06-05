package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link JournalEntryController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class JournalEntryControllerIntegrationTest {

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID CONFIDENTIALITY_LEVEL_ID = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	@Autowired
	private transient PersonJournalEntryController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient PersonService personService;

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
	 * Test that the {@link JournalEntryController#get(UUID, UUID)} action
	 * returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception { // NOPMD
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalEntryTO obj = controller.get(PERSON_ID,
				UUID.randomUUID());

		assertNull(
				"Returned JournalEntryTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link JournalEntryController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception { // NOPMD
		final Collection<JournalEntryTO> list = controller.getAll(
				PERSON_ID,
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the {@link JournalEntryController#create(UUID, JournalEntryTO)} and
	 * {@link JournalEntryController#delete(UUID, UUID)} actions.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws Exception { // NOPMD
		// Now create JournalEntry for testing
		final JournalEntryTO obj = new JournalEntryTO();
		obj.setPersonId(PERSON_ID);
		obj.setEntryDate(new Date());
		obj.setJournalSource(new ReferenceLiteTO<JournalSource>(UUID
				.fromString("b2d07973-5056-a51a-8073-1d3641ce507f"), ""));
		obj.setJournalTrack(new ReferenceLiteTO<JournalTrack>(UUID
				.fromString("b2d07a7d-5056-a51a-80a8-96ae5188a188"), ""));
		obj.setConfidentialityLevel(new ConfidentialityLevelLiteTO(
				CONFIDENTIALITY_LEVEL_ID, ""));
		obj.setObjectStatus(ObjectStatus.ACTIVE);

		final JournalEntryTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("PersonIds did not match.", PERSON_ID, saved.getPersonId());

		session.clear();

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		try {
			// ObjectNotFoundException expected at this point
			final JournalEntryTO afterDeletion = controller.get(savedId,
					PERSON_ID);
			assertNull(
					"Instance should not be able to get loaded after it has been deleted.",
					afterDeletion);
		} catch (final ObjectNotFoundException exc) { // NOPMD by jon.adams
			// expected
		}
	}
}