package org.jasig.ssp.service.impl; // NOPMD by jon.adams on 5/24/12 2:17 PM

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.hibernate.SessionFactory;
import org.jasig.ssp.config.MailConfig;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import com.google.common.collect.Sets;

/**
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class EarlyAlertServiceTest {

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final String PERSON_FULLNAME = "James Gosling";

	private static final String PERSON_CREATEDBY_FULLNAME = "System Administrator";

	private static final UUID EARLY_ALERT_SUGGESTION_ID = UUID
			.fromString("b2d11141-5056-a51a-80c1-c1250ba820f8");

	private static final String EARLY_ALERT_SUGGESTION_NAME = "See Instructor";

	private static final String EARLY_ALERT_COURSE_NAME = "Complicated Science 101";

	private static final UUID EARLY_ALERT_SUGGESTION_DELETED_ID = UUID
			.fromString("881DF3DD-1AA6-4CB8-8817-E95DAF49227A");

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MailConfig smtpServerConfig;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

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
	 * Test that method
	 * {@link org.jasig.ssp.service.impl.EarlyAlertServiceImpl#create(org.jasig.ssp.model.EarlyAlert)}
	 * generates the expected messages.
	 * 
	 * @throws ValidationException
	 *             Thrown if any data objects are not valid.
	 * @throws ObjectNotFoundException
	 *             Thrown if any reference data could not be loaded.
	 * @throws SendFailedException
	 *             Thrown if mail sends threw any exceptions.
	 */
	@Test
	public void testCreateEarlyAlert() throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		final SimpleSmtpServer smtpServer = smtpServerConfig.spawn();
		try {
			assertFalse("Faux mail server should be running but was not.",
					smtpServer.isStopped());

			// arrange
			final EarlyAlert obj = arrangeEarlyAlert();

			// act
			earlyAlertService.create(obj);
			sessionFactory.getCurrentSession().flush();

			// Try to send all messages to the fake server.
			messageService.sendQueuedMessages();

			// assert
			assertEquals("Sent message count did not match.", 3,
					smtpServer.getReceivedEmailSize());
			final SmtpMessage message = (SmtpMessage) smtpServer
					.getReceivedEmail()
					.next();
			assertTrue(
					"Message subject did not match. Was: "
							+ message.getHeaderValue("Subject"),
					message.getHeaderValue("Subject").contains(
							"Notice - " + PERSON_FULLNAME + " : "));
			assertTrue("Message body did not match. Was: " + message.getBody(),
					message.getBody().contains(PERSON_CREATEDBY_FULLNAME) &&
							message.getBody().contains(PERSON_FULLNAME) &&
							message.getBody().contains(EARLY_ALERT_COURSE_NAME));
		} finally {
			smtpServer.stop();
		}
	}

	/**
	 * @return
	 * @throws ObjectNotFoundException
	 */
	private EarlyAlert arrangeEarlyAlert() throws ObjectNotFoundException {
		final EarlyAlert obj = new EarlyAlert();
		obj.setPerson(personService.get(PERSON_ID));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setClosedById(PERSON_ID);
		obj.setCourseName(EARLY_ALERT_COURSE_NAME);
		obj.setCampus(campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1")));

		final Set<EarlyAlertSuggestion> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestion(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME,
				"description", (short) 0)); // NOPMD by jon.adams on 5/21/12
		final EarlyAlertSuggestion deletedSuggestion = new EarlyAlertSuggestion(
				EARLY_ALERT_SUGGESTION_DELETED_ID,
				"EARLY_ALERT_SUGGESTION_DELETED_NAME", "description",
				(short) 0); // NOPMD
		deletedSuggestion.setObjectStatus(ObjectStatus.DELETED);
		earlyAlertSuggestionIds.add(deletedSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);
		return obj;
	}

	@Test(expected = ValidationException.class)
	public void testCreateEarlyAlertInvalidPerson()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// arrange
		final EarlyAlert obj = arrangeEarlyAlert();
		obj.setPerson(null);

		// act
		earlyAlertService.create(obj);

		// assert
		fail("Should have thrown a ValidationException.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEarlyAlertInvalidEarlyAlert()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// act
		earlyAlertService.create(null);

		// assert
		fail("Should have thrown a ValidationException.");
	}
}