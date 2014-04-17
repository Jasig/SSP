/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl; // NOPMD by jon.adams on 5/24/12 2:17 PM

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.hibernate.SessionFactory;
import org.jasig.ssp.config.MockMailService;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.util.service.stub.Stubs;
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
		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);

		// act
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages(null);

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
						"Notice - " + student0FullName() + " : "));
		assertTrue("Message body did not match. Was: " + message.getBody(),
				message.getBody().contains(systemFullName()) &&
						message.getBody().contains(student0FullName()) &&
						message.getBody().contains(obj.getCourseName()));
	}

	@Test(expected = ValidationException.class)
	public void testCreateEarlyAlertInvalidPerson()
			throws ObjectNotFoundException,
			ValidationException, SendFailedException {
		// arrange
		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);
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
		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);
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
		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);
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
		final EarlyAlertRouting route1 = Stubs.arrangeEarlyAlertRouting(campusService);
		route1.setPerson(personService.get(student0Id()));
		route1.setGroupName(systemFullName());
		route1.setGroupEmail("test@example.com");

		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);

		// act
		earlyAlertRoutingService.create(route1);
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages(null);

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
		final EarlyAlertRouting route1 = Stubs.arrangeEarlyAlertRouting(campusService);
		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);

		// act
		earlyAlertRoutingService.create(route1);
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Send all messages to the fake server.
		messageService.sendQueuedMessages(null);

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

		final EarlyAlert obj = Stubs.arrangeEarlyAlert(personService, campusService);
		obj.setCourseName("MTH101"); // will resolve to v_external_course record
		obj.setCreatedBy(new AuditPerson(kenId()));

		// act
		earlyAlertService.create(obj);
		sessionFactory.getCurrentSession().flush();

		// Try to send all messages to the fake server.
		messageService.sendQueuedMessages(null);

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
		Person alertedOnPerson = personService.get(dmrId());
		assertEquals("This test requires that the alerted-on person have no"
				+ " program status", 0,
				alertedOnPerson.getProgramStatuses().size());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
		final Set<PersonProgramStatus> programStatuses =
				alertedOnPersonAfterAlert.getProgramStatuses();
		assertNotNull("No program status set on alerted on person", programStatuses);
		assertEquals("Incorrect number of program statuses set on alerted on person",
				1, programStatuses.size());
		assertEquals("Did not set active program status on alerted on person",
				Stubs.ProgramStatusFixture.ACTIVE.id(),
				programStatuses.iterator().next().getProgramStatus().getId());
		assertNull("Active program status expired",
				programStatuses.iterator().next().getExpirationDate());
	}

	@Test
	public void testEarlyAlertUndeletesAlertedOnUser()
			throws ObjectNotFoundException, ValidationException {

		personService.delete(dmrId());
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person alertedOnPerson = personService.get(dmrId());
		assertEquals("This test requires that the alerted on person be in a"
				+ " deleted state.", ObjectStatus.INACTIVE,
				alertedOnPerson.getObjectStatus());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
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
		final Person alertedOnPerson = personService.get(dmrId());
		assertEquals("This test requires that the alerted-on person have no"
				+ " program status", 0,
				alertedOnPerson.getProgramStatuses().size());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setClosedBy(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
		final Set<PersonProgramStatus> programStatuses =
				alertedOnPersonAfterAlert.getProgramStatuses();
		// sanity check
		assertEquals("Did not set active program status on alerted on person",
				Stubs.ProgramStatusFixture.ACTIVE.id(),
				programStatuses.iterator().next().getProgramStatus().getId());
		assertNull("Active program status expired",
				programStatuses.iterator().next().getExpirationDate());

		// set person to inactive program status
		final PersonProgramStatus personProgramStatus = new PersonProgramStatus();
		personProgramStatus.setEffectiveDate(new Date());
		personProgramStatus.setProgramStatus(
				programStatusService.get(Stubs.ProgramStatusFixture.INACTIVE.id()));
		personProgramStatus.setPerson(alertedOnPersonAfterAlert);
		programStatuses.add(personProgramStatus);
		// save should cascade, but make sure custom create logic fires
		personProgramStatusService.create(personProgramStatus);
		personService.save(alertedOnPersonAfterAlert);
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person alertedOnPersonAfterStatusChange = personService.get(dmrId());
		final Set<PersonProgramStatus> programStatusesAfterChange =
				alertedOnPersonAfterStatusChange.getProgramStatuses();
		assertEquals("Should have added a program status", 2,
				programStatusesAfterChange.size());
		for ( PersonProgramStatus status : programStatusesAfterChange ) {
			if ( status.getProgramStatus().getId().equals(
					Stubs.ProgramStatusFixture.INACTIVE.id()) ) {
				assertNull("Inactive program status should be non-expired",
						status.getExpirationDate());
			} else if ( status.getProgramStatus().getId().equals(
					Stubs.ProgramStatusFixture.ACTIVE.id()) ) {
				assertNotNull("Should have expired active program status",
						status.getExpirationDate());
			} else { // only two element
				fail("Unexpected program status " + status.getProgramStatus());
			}
		}

		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedBy(personService.get(student0Id()));
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(dmrId());
		final Set<PersonProgramStatus> programStatusesAfterClose =
				alertedOnPersonAfterAlertClose.getProgramStatuses();
		assertEquals("Closing an early alert should not change person program status",
				2,
				programStatusesAfterChange.size());
		for ( PersonProgramStatus status : programStatusesAfterClose ) {
			if ( status.getProgramStatus().getId().equals(
					Stubs.ProgramStatusFixture.INACTIVE.id()) ) {
				assertNull("Inactive program status should be non-expired",
						status.getExpirationDate());
			} else if ( status.getProgramStatus().getId().equals(
					Stubs.ProgramStatusFixture.ACTIVE.id()) ) {
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
		final Person alertedOnPerson = personService.get(dmrId());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setClosedBy(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		personService.delete(dmrId());
		sessionFactory.getCurrentSession().flush();

		// sanity check
		final Person deletedPerson = personService.get(dmrId());
		assertEquals("This test requires that the alerted on person be in a"
				+ " deleted state.", ObjectStatus.INACTIVE,
				deletedPerson.getObjectStatus());

		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedBy(personService.get(student0Id()));
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(dmrId());
		assertEquals("Editing an early alert should not undelete the alerted on person.",
				ObjectStatus.INACTIVE,
				alertedOnPersonAfterAlertClose.getObjectStatus());
	}

	@Test
	public void testEarlyAlertSetsEarlyAlertStudentTypeIfAlertedOnUserHasNoType()
			throws ObjectNotFoundException, ValidationException {
		// sanity check
		Person alertedOnPerson = personService.get(dmrId());
		alertedOnPerson.setStudentType(null);
		personService.save(alertedOnPerson);
		sessionFactory.getCurrentSession().flush();

		alertedOnPerson = personService.get(dmrId());

		assertNull("This test requires that the alerted-on person have no"
				+ " student type", alertedOnPerson.getStudentType());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
		final StudentType studentType = alertedOnPersonAfterAlert.getStudentType();
		assertNotNull("No student type set on alerted on person", studentType);
		assertEquals("Did not set active program status on alerted on person",
				Stubs.StudentTypeFixture.EAL.id(),
				studentType.getId());

	}

	@Test
	public void testCreatingAndUpdatingEarlyAlertDoesNotChangeNonNullStudentType()
			throws ObjectNotFoundException, ValidationException {

		final Person alertedOnPerson = personService.get(dmrId());

		final StudentType initialStudentType = alertedOnPerson.getStudentType();
		assertNotNull("This test requires that the alerted-on person have"
				+ " a student type", initialStudentType);
		assertThat("This test requires that the alerted-on person have"
				+ " a non-EAL student type", initialStudentType.getId(),
				not(equalTo(Stubs.StudentTypeFixture.EAL.id())));

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setClosedBy(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
		final StudentType studentTypeAfterAlert = alertedOnPersonAfterAlert.getStudentType();
		assertNotNull("Alerted-on person should have retained a student type after alert",
				studentTypeAfterAlert);
		assertEquals("Alerted-on person should have retained the same student type after alert",
				initialStudentType.getId(), studentTypeAfterAlert.getId());


		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedBy(personService.get(student0Id()));
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(dmrId());
		final StudentType studentTypeAfterAlertClose = alertedOnPersonAfterAlert.getStudentType();
		assertNotNull("Alerted-on person should have retained a student type after alert close",
				studentTypeAfterAlertClose);
		assertEquals("Alerted-on person should have retained the same student type after alert close",
				initialStudentType.getId(), studentTypeAfterAlertClose.getId());

	}

	// similar to testCreatingAndUpdatingEarlyAlertDoesNotChangeNonNullStudentType()
	// but checks for the null student type case, i.e. if someone goes and
	// removes a student type for whatever reason, just changing an existing
	// early alert isn't expected to set the student type back... would take
	// another early alert
	@Test
	public void testUpdatingEarlyAlertDoesNotSetStudentType()
			throws ObjectNotFoundException, ValidationException {

		final Person alertedOnPerson = personService.get(dmrId());

		final EarlyAlert proposedEarlyAlert = Stubs.arrangeEarlyAlert(personService, campusService);
		proposedEarlyAlert.setClosedBy(null);
		proposedEarlyAlert.setPerson(alertedOnPerson);
		final EarlyAlert createdEarlyAlert =
				earlyAlertService.create(proposedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		final Person alertedOnPersonAfterAlert = personService.get(dmrId());
		alertedOnPersonAfterAlert.setStudentType(null);
		personService.save(alertedOnPersonAfterAlert);
		sessionFactory.getCurrentSession().flush();

		// now close the alert
		final EarlyAlert loadedEarlyAlert =
				earlyAlertService.get(createdEarlyAlert.getId());
		loadedEarlyAlert.setClosedBy(personService.get(student0Id()));
		earlyAlertService.save(loadedEarlyAlert);
		sessionFactory.getCurrentSession().flush();

		// and the actual assert of interest
		final Person alertedOnPersonAfterAlertClose = personService.get(dmrId());
		assertNull("Only creating an alert should set a user's student type.",
				alertedOnPersonAfterAlertClose.getStudentType());
	}

	private String student0FullName() {
		return Stubs.PersonFixture.STUDENT_0.fullName();
	}

	private UUID student0Id() {
		return Stubs.PersonFixture.STUDENT_0.id();
	}

	private UUID dmrId() {
		return Stubs.PersonFixture.DMR.id();
	}

	private UUID kenId() {
		return Stubs.PersonFixture.KEN.id();
	}

	private String systemFullName() {
		return Stubs.PersonFixture.SYSTEM.fullName();
	}

}