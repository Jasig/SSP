package org.jasig.ssp.service.impl; // NOPMD by jon.adams

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.hibernate.SessionFactory;
import org.jasig.ssp.config.MockMailService;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
public class EarlyAlertResponseServiceTest {

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

	private static final String EARLY_ALERT_RESPONSE_COMMENT = "Comment goes here";

	private static final String EARLY_ALERT_RESPONSE_OUTCOME_OTHER = "Other";

	private static final UUID EARLY_ALERT_OUTREACH_ID = UUID
			.fromString("e7908476-e67d-4fb2-890b-2d4e6c9b0e42");

	private static final String EARLY_ALERT_OUTREACH_NAME = "Text";

	public final static UUID EARLY_ALERT_OUTCOME_STUDENTRESPONDED_ID = UUID
			.fromString("12a58804-45dc-40f2-b2f5-d7e4403acee1");

	@Autowired
	private transient EarlyAlertResponseService service;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MockMailService mockMailService;

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
	 * {@link org.jasig.ssp.service.impl.EarlyAlertResponseServiceImpl#create(org.jasig.ssp.model.EarlyAlertResponse)}
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
	public void testCreateEarlyAlertResponse() throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// arrange
		final EarlyAlertResponse obj = arrangeEarlyAlertResponse();

		// act
		earlyAlertService.create(obj.getEarlyAlert());
		service.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages();

		// assert
		assertEquals("Sent message count did not match.", 4,
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
	}

	/**
	 * Test that method
	 * {@link org.jasig.ssp.service.impl.EarlyAlertResponseServiceImpl#create(org.jasig.ssp.model.EarlyAlertResponse)}
	 * generates the expected Journal Entry.
	 * 
	 * @throws ValidationException
	 *             Thrown if any data objects are not valid.
	 * @throws ObjectNotFoundException
	 *             Thrown if any reference data could not be loaded.
	 * @throws SendFailedException
	 *             Thrown if mail sends threw any exceptions.
	 */
	@Test
	public void testCreateEarlyAlertResponseJournalEntry()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// arrange
		final EarlyAlertResponse obj = arrangeEarlyAlertResponse();

		// act
		earlyAlertService.create(obj.getEarlyAlert());
		service.create(obj);
		sessionFactory.getCurrentSession().flush();

		// assert

		// journal entries not attached to student but the coach (advisor)
		final PagingWrapper<JournalEntry> entriesForStudent = journalEntryService
				.getAllForPerson(obj.getEarlyAlert().getPerson(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
		assertEquals("Journal Entry count did not match.", 0,
				entriesForStudent.getResults());

		// load all journal entries for the coach (advisor)
		final PagingWrapper<JournalEntry> entries = journalEntryService
				.getAllForPerson(obj.getEarlyAlert().getPerson().getCoach(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		JournalEntry journalEntry = null;

		for (final JournalEntry entry : entries) {
			if (entry.getJournalSource().getId() == JournalSource.JOURNALSOURCE_EARLYALERT_ID) {
				journalEntry = entry;
			}
		}

		assertNotNull("JournalEntry should not have been null.", journalEntry);

		assertEquals(
				"Entry Confidentiality Level did not match.",
				ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE,
				journalEntry
						.getConfidentialityLevel().getId());
		assertEquals("JournalEntry Track did not match.",
				JournalTrack.JOURNALTRACK_EARLYALERT_ID,
				journalEntry.getJournalTrack().getId());

		final String generatedText = journalEntry.getComment();

		assertTrue(
				"Entry comment did not match. Was: " + generatedText,
				generatedText.contains(PERSON_CREATEDBY_FULLNAME)
						&& generatedText.contains(
								EARLY_ALERT_SUGGESTION_NAME)
						&& generatedText.contains(
								EARLY_ALERT_COURSE_NAME)
						&& generatedText.contains(configService
								.getByNameException("term_to_represent_early_alert")));
	}

	/**
	 * @return
	 * @throws ObjectNotFoundException
	 */
	private EarlyAlertResponse arrangeEarlyAlertResponse()
			throws ObjectNotFoundException {
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

		final EarlyAlertResponse response = new EarlyAlertResponse();
		response.setEarlyAlert(obj);
		response.setObjectStatus(ObjectStatus.ACTIVE);
		response.setComment(EARLY_ALERT_RESPONSE_COMMENT);
		response.setEarlyAlertOutcome(earlyAlertOutcomeService
				.get(EARLY_ALERT_OUTCOME_STUDENTRESPONDED_ID));
		response.setEarlyAlertOutcomeOtherDescription(EARLY_ALERT_RESPONSE_OUTCOME_OTHER);

		final Set<EarlyAlertOutreach> earlyAlertOutreachIds = Sets
				.newHashSet();
		earlyAlertOutreachIds.add(new EarlyAlertOutreach(
				EARLY_ALERT_OUTREACH_ID, EARLY_ALERT_OUTREACH_NAME,
				"description", (short) 0)); // NOPMD by jon.adams on 5/21/12

		response.setEarlyAlertOutreachIds(earlyAlertOutreachIds);

		return response;
	}

	@Test(expected = ValidationException.class)
	public void testCreateEarlyAlertResponseInvalidPerson()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// arrange
		final EarlyAlertResponse obj = arrangeEarlyAlertResponse();
		obj.getEarlyAlert().setPerson(null);

		// act
		service.create(obj);

		// assert
		fail("Should have thrown a ValidationException.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEarlyAlertResponseInvalidEarlyAlertResponse()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// act
		service.create(null);

		// assert
		fail("Should have thrown a ValidationException.");
	}
}