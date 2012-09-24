package org.jasig.ssp.service.impl; // NOPMD by jon.adams on 5/24/12 2:17 PM

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.hibernate.SessionFactory;
import org.jasig.ssp.config.MockMailService;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.ProgramStatusService;
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
@TransactionConfiguration
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

	private static final UUID EARLY_ALERT_REASON_ID = UUID
			.fromString("b2d11335-5056-a51a-80ea-074f8fef94ea");

	private static final String INSTRUCTOR_ID = "f549ecab-5110-4cc1-b2bb-369cac854dea";

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient EarlyAlertRoutingService earlyAlertRoutingService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient MockMailService mockMailService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Autowired
	private transient ProgramStatusService programStatusService;

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

		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
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
		assertEquals("Sent message count did not match.", 2,
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

	/**
	 * Test that a invalid coach (exists but no ID) and no EA Coordinator for
	 * the campus, will throw a ValidationException.
	 * 
	 * <p>
	 * Note: Once the default EA Coordinator (global setting) is enabled, this
	 * test may need updated.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Expected exception for this test
	 * @throws SendFailedException
	 *             Should not be thrown for this test.
	 */
	@Test(expected = ValidationException.class)
	public void testCreateEarlyAlertInvalidCoachAndEACoord()
			throws ObjectNotFoundException, ValidationException,
			SendFailedException {
		// arrange
		final EarlyAlert obj = arrangeEarlyAlert();
		// coach w/o ID (doesn't make sense most of the time — for testing only)
		final Person coachWithoutId = new Person();
		coachWithoutId.setFirstName("1");
		coachWithoutId.setLastName("2");
		coachWithoutId.setUsername("3");
		coachWithoutId.setPrimaryEmailAddress("4");
		coachWithoutId.setSchoolId("5");
		obj.getPerson().setCoach(coachWithoutId);
		obj.getCampus().setEarlyAlertCoordinatorId(null);

		// act
		earlyAlertService.create(obj);

		// assert
		fail("Should have thrown a ValidationException. (Once default/global EA Coord enabled, this test may need updated.)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEarlyAlertMissingCampus()
			throws ObjectNotFoundException, ValidationException,
			SendFailedException {
		// arrange
		final EarlyAlert obj = arrangeEarlyAlert();
		obj.getPerson().setCoach(new Person());
		obj.setCampus(null);

		// act
		earlyAlertService.create(obj);

		// assert
		fail("Should have thrown a IllegalArgumentException.");
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

	@Test
	public void testCreateEarlyAlertRoutings() throws ObjectNotFoundException,
			ValidationException, SendFailedException {

		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// arrange
		final EarlyAlertRouting route1 = arrangeEarlyAlertRouting();
		route1.setPerson(personService.get(PERSON_ID));
		route1.setGroupName(PERSON_FULLNAME);
		route1.setGroupEmail("test@example.com");

		final EarlyAlert obj = arrangeEarlyAlert();

		// act
		earlyAlertRoutingService.create(route1);
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages();

		// assert
		assertEquals(
				"Sent message count should have contained the 2 main ones plus 2 routed messages.",
				4, smtpServer.getReceivedEmailSize());
	}

	@Test
	public void testCreateEarlyAlertEmptyRoute()
			throws ObjectNotFoundException, ValidationException {

		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// arrange
		final EarlyAlertRouting route1 = arrangeEarlyAlertRouting();
		final EarlyAlert obj = arrangeEarlyAlert();

		// act
		earlyAlertRoutingService.create(route1);
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Send all messages to the fake server.
		messageService.sendQueuedMessages();

		// assert
		assertEquals(
				"Sent message count should have only been the 2 main ones, and no extra routes.",
				2, smtpServer.getReceivedEmailSize());
	}

	@Test
	public void testTermAndCourseExposedToMessageRenderer()
			throws ObjectNotFoundException, ValidationException {
		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// arrange
		final String testBody =
				"Term name: $term.name, Course title: $course.title";
		MessageTemplate template1 =
				messageTemplateService.getByName("Early Alert Confirmation to Faculty");
		template1.setBody(testBody);
		messageTemplateService.save(template1);

		MessageTemplate template2 =
				messageTemplateService.getByName("Early Alert Confirmation to Advisor");
		template2.setBody(testBody);
		messageTemplateService.save(template2);

		final EarlyAlert obj = arrangeEarlyAlert();
		obj.setCourseName("MTH101"); // will resolve to v_external_course record
		obj.setCreatedBy(personService.get(UUID.fromString(INSTRUCTOR_ID)));

		// act
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages();

		// assert
		assertEquals("Sent message count did not match.", 2,
				smtpServer.getReceivedEmailSize());
		@SuppressWarnings("unchecked")
		final Iterator<SmtpMessage> receivedMessages = smtpServer.getReceivedEmail();
		final SmtpMessage message1 = receivedMessages.next();
		assertEquals("Term name: Fall 2012, Course title: College Algebra", message1.getBody());
		final SmtpMessage message2 = receivedMessages.next();
		assertEquals("Term name: Fall 2012, Course title: College Algebra", message2.getBody());
	}

	@Test
	public void testEarlyAlertSetsActiveProgramStatusIfAlertedOnUserHasNoStatus()
			throws ObjectNotFoundException, ValidationException {

		// sanity check
		Person alertedOnPerson = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("This test requires that the alerted-on person have no"
				+ " program status", 0,
				alertedOnPerson.getProgramStatuses().size());

		final EarlyAlert proposedEarlyAlert = arrangeEarlyAlert();
		proposedEarlyAlert.setPerson(alertedOnPerson);
		earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		final Set<PersonProgramStatus> programStatuses =
				alertedOnPersonAfterAlert.getProgramStatuses();
		assertNotNull("No program status set on alerted on person", programStatuses);
		assertEquals("Incorrect number of program statuses set on alerted on person",
				1, programStatuses.size());
		assertEquals("Did not set active program status on alerted on person",
				UUID.fromString("b2d12527-5056-a51a-8054-113116baab88"),
				programStatuses.iterator().next().getProgramStatus().getId());
		assertNull("Active program status expired",
				programStatuses.iterator().next().getExpirationDate());
	}

	@Test
	public void testEarlyAlertUndeletesAlertedOnUser()
			throws ObjectNotFoundException, ValidationException {

		personService.delete(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person alertedOnPerson = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("This test requires that the alerted on person be in a"
				+ " deleted state.", ObjectStatus.INACTIVE,
				alertedOnPerson.getObjectStatus());

		final EarlyAlert proposedEarlyAlert = arrangeEarlyAlert();
		proposedEarlyAlert.setPerson(alertedOnPerson);
		earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("Alerted on person should be undeleted",
				ObjectStatus.ACTIVE,
				alertedOnPersonAfterAlert.getObjectStatus());
	}

	// don't want updates to do anything to user status... as currently used
	// these ops are really just for closing EAs. so rather than write logic
	// to try to figure out exactly what changed and whether or not it justifies
	// user program status activation, we just skip that processing altogether
	@Test
	public void testUpdatingEarlyAlertDoesNotSetActiveProgramStatusOnAlertedOnUser()
			throws ObjectNotFoundException, ValidationException {
		// sanity check
		final Person alertedOnPerson = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("This test requires that the alerted-on person have no"
				+ " program status", 0,
				alertedOnPerson.getProgramStatuses().size());

		final EarlyAlert proposedEarlyAlert = arrangeEarlyAlert();
		proposedEarlyAlert.setClosedById(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		final Set<PersonProgramStatus> programStatuses =
				alertedOnPersonAfterAlert.getProgramStatuses();
		// sanity check
		assertEquals("Did not set active program status on alerted on person",
				UUID.fromString("b2d12527-5056-a51a-8054-113116baab88"),
				programStatuses.iterator().next().getProgramStatus().getId());
		assertNull("Active program status expired",
				programStatuses.iterator().next().getExpirationDate());

		// set person to inactive program status
		final PersonProgramStatus personProgramStatus = new PersonProgramStatus();
		personProgramStatus.setEffectiveDate(new Date());
		personProgramStatus.setProgramStatus(
				programStatusService.get(UUID.
						fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1")));
		personProgramStatus.setPerson(alertedOnPersonAfterAlert);
		programStatuses.add(personProgramStatus);
		// save should cascade, but make sure custom create logic fires
		personProgramStatusService.create(personProgramStatus);
		personService.save(alertedOnPersonAfterAlert);
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person alertedOnPersonAfterStatusChange = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		final Set<PersonProgramStatus> programStatusesAfterChange =
				alertedOnPersonAfterStatusChange.getProgramStatuses();
		assertEquals("Should have added a program status", 2,
				programStatusesAfterChange.size());
		for ( PersonProgramStatus status : programStatusesAfterChange ) {
			if ( status.getProgramStatus().getId().equals(
					UUID.fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1")) ) {
				assertNull("Inactive program status should be non-expired",
						status.getExpirationDate());
			} else if ( status.getProgramStatus().getId().equals(
					UUID.fromString("b2d12527-5056-a51a-8054-113116baab88")) ) {
				assertNotNull("Should have expired active program status",
						status.getExpirationDate());
			} else { // only two element
				fail("Unexpected program status " + status.getProgramStatus());
			}
		}

		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedById(PERSON_ID);
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		final Set<PersonProgramStatus> programStatusesAfterClose =
				alertedOnPersonAfterAlertClose.getProgramStatuses();
		assertEquals("Closing an early alert should not change person program status",
				2,
				programStatusesAfterChange.size());
		for ( PersonProgramStatus status : programStatusesAfterClose ) {
			if ( status.getProgramStatus().getId().equals(
					UUID.fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1")) ) {
				assertNull("Inactive program status should be non-expired",
						status.getExpirationDate());
			} else if ( status.getProgramStatus().getId().equals(
					UUID.fromString("b2d12527-5056-a51a-8054-113116baab88")) ) {
				assertNotNull("Should have expired active program status",
						status.getExpirationDate());
			} else { // only two element
				fail("Unexpected program status " + status.getProgramStatus());
			}
		}

	}

	// see comments on testUpdatingEarlyAlertDoesNotSetActiveProgramStatusOnAlertedOnUser()
	@Test
	public void testUpdatingEarlyAlertDoesNotUndeleteAlertedOnUser()
			throws ObjectNotFoundException, ValidationException {
		// sanity check
		final Person alertedOnPerson = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));

		final EarlyAlert proposedEarlyAlert = arrangeEarlyAlert();
		proposedEarlyAlert.setClosedById(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		personService.delete(UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person deletedPerson = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("This test requires that the alerted on person be in a"
				+ " deleted state.", ObjectStatus.INACTIVE,
				deletedPerson.getObjectStatus());

		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedById(PERSON_ID);
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(
				UUID.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"));
		assertEquals("Editing an early alert should not undelete the alerted on person.",
				ObjectStatus.INACTIVE,
				alertedOnPersonAfterAlertClose.getObjectStatus());
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
		deletedSuggestion.setObjectStatus(ObjectStatus.INACTIVE);
		earlyAlertSuggestionIds.add(deletedSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		final Set<EarlyAlertReason> earlyAlertReasonIds = Sets
				.newHashSet();
		earlyAlertReasonIds.add(new EarlyAlertReason(EARLY_ALERT_REASON_ID,
				"name", "description", (short) 0)); // NOPMD by jon.adams
		obj.setEarlyAlertReasonIds(earlyAlertReasonIds);

		return obj;
	}

	/**
	 * @return An EarlyAlertRouting with Campus and EarlyAlertReason, but no
	 *         Person or group information set (left null).
	 * @throws ObjectNotFoundException
	 */
	private EarlyAlertRouting arrangeEarlyAlertRouting()
			throws ObjectNotFoundException {
		final EarlyAlertRouting obj = new EarlyAlertRouting();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setCampus(campusService.get(UUID
				.fromString("901E104B-4DC7-43F5-A38E-581015E204E1")));
		obj.setEarlyAlertReason(new EarlyAlertReason(EARLY_ALERT_REASON_ID,
				"name", "description", (short) 0)); // NOPMD by jon.adams
		return obj;
	}
}