/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.jasig.ssp.config.EarlyAlertResponseReminderRecipientsConfig;
import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Base class for JUnit test classes used to test {@link EarlyAlertServiceImpl} sendAllEarlyAlertReminderNotifications method.
 */
public abstract class SendAllEarlyAlertReminderNotificationsTestBase {

	@Mock protected Campus campus;
	@Mock protected ConfigService configService;
	@Mock protected AuditPerson earlyAlertCreatorAuditPerson;
	@Mock protected Person earlyAlertCreatorPerson;
	@Mock protected EarlyAlertDao earlyAlertDao;
	@Mock protected EarlyAlertResponseReminderRecipientsConfig config;
	@Mock protected MessageService messageService;
	@Mock protected MessageTemplateService messageTemplateService;
	@Mock protected PersonService personService;
	@Mock protected SubjectAndBody subjectAndBody;

	protected Person coach;
	protected Person earlyAlertCoordinator;
	protected List<EarlyAlert> earlyAlerts;

	@InjectMocks protected EarlyAlertServiceImpl service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.earlyAlerts = new ArrayList<EarlyAlert>();
		this.setMaximumDaysBeforeEarlyAlertResponseConfig(2);
		this.setTermToRepresentEarlyAlertConfig("EarlyAlert");
		this.setUpEarlyAlertCreator();
		given(this.messageTemplateService.createEarlyAlertResponseRequiredToCoachMessage(org.mockito.Matchers.anyMap())).willReturn(this.subjectAndBody);
		given(this.earlyAlertDao.getResponseDueEarlyAlerts(any(Date.class))).willReturn(this.earlyAlerts);
		this.additionalSetUp();
	}

	protected abstract void additionalSetUp() throws Exception;

	@After
	public void tearDown() throws Exception {
		this.additionalTearDown();
	}

	protected abstract void additionalTearDown() throws Exception;

	// scenarios

	protected void studentHasCoachAndEarlyAlertHasNoCampusScenario() throws Exception {
		this.coach = this.createMockPerson();
		final Person student = this.createMockStudentWithCoach(this.coach);
		this.addEarlyAlertWithNoCampusForStudent(student);
	}

	protected void studentHasCoachAndEarlyAlertCampusHasNoEacIdScenario() throws Exception {
		this.campusHasNoEacId(this.campus);
		this.coach = this.createMockPerson();
		final Person student = this.createMockStudentWithCoach(this.coach);
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	protected void studentHasCoachAndEarlyAlertCampusHasInvalidEacIdScenario() throws Exception {
		final UUID eacUuid = UUID.randomUUID();
		this.campusHasInvalidEacId(this.campus, eacUuid);
		this.coach = this.createMockPerson();
		final Person student = this.createMockStudentWithCoach(this.coach);
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	protected void studentHasCoachAndEarlyAlertCampusHasValidEacIdScenario() throws Exception {
		final UUID eacUuid = UUID.randomUUID();
		this.earlyAlertCoordinator = this.createMockPerson();
		this.campusHasValidEacId(this.campus, eacUuid, this.earlyAlertCoordinator);
		this.coach = this.createMockPerson();
		final Person student = this.createMockStudentWithCoach(this.coach);
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	protected void studentHasNoCoachAndEarlyAlertHasNoCampusScenario() throws Exception {
		final Person student = this.createMockStudentWithNoCoach();
		this.addEarlyAlertWithNoCampusForStudent(student);
	}

	protected void studentHasNoCoachAndEarlyAlertCampusHasNoEacIdScenario() throws Exception {
		this.campusHasNoEacId(this.campus);
		final Person student = this.createMockStudentWithNoCoach();
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	protected void studentHasNoCoachAndEarlyAlertCampusHasInvalidEacIdScenario() throws Exception {
		final UUID eacUuid = UUID.randomUUID();
		this.campusHasInvalidEacId(this.campus, eacUuid);
		final Person student = this.createMockStudentWithNoCoach();
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	protected void studentHasNoCoachAndEarlyAlertCampusHasValidEacScenario() throws Exception {
		final UUID eacUuid = UUID.randomUUID();
		this.earlyAlertCoordinator = this.createMockPerson();
		this.campusHasValidEacId(this.campus, eacUuid, this.earlyAlertCoordinator);
		final Person student = this.createMockStudentWithCoach(null);
		this.addEarlyAlertWithCampusForStudent(student, this.campus);
	}

	// verify

	protected void verifyMessageSentToEarlyAlertCoordinator() throws Exception {
		this.verifyMessageSentToPerson(this.earlyAlertCoordinator);
	}

	protected void verifyMessageSentToCoach() throws Exception {
		this.verifyMessageSentToPerson(this.coach);
	}

	protected void verifyNoMessageSentToEarlyAlertCoordinator() throws Exception {
		this.verifyNoMessageSentToPerson(this.earlyAlertCoordinator);
	}

	protected void verifyNoMessageSentToCoach() throws Exception {
		this.verifyNoMessageSentToPerson(this.coach);
	}

	protected void verifyNoMessagesSent() throws Exception {
		verify(this.messageService, never()).createMessage(any(Person.class), eq((String)null), eq(this.subjectAndBody));
	}

	protected void verifyMessageSentToPerson(final Person person) throws Exception {
		verify(this.messageService).createMessage(person,  null, this.subjectAndBody);
	}

	protected void verifyNoMessageSentToPerson(final Person person) throws Exception {
		verify(this.messageService, never()).createMessage(person,  null, this.subjectAndBody);
	}

	// service call

	protected void earlyAlertReminderNotificationsAreTriggered() {
		this.service.sendAllEarlyAlertReminderNotifications();
	}

	// helper methods

	protected void setMaximumDaysBeforeEarlyAlertResponseConfig(int days) {
		given(this.configService.getByNameNull("maximum_days_before_early_alert_response")).willReturn(String.valueOf(days));
	}

	protected void setTermToRepresentEarlyAlertConfig(final String term) {
		given(this.configService.getByNameEmpty("term_to_represent_early_alert")).willReturn(term);
	}

	protected void setUpEarlyAlertCreator() throws Exception {
		final UUID id = UUID.randomUUID();
		given(this.earlyAlertCreatorAuditPerson.getId()).willReturn(id);
		given(this.earlyAlertCreatorPerson.getId()).willReturn(id);
		given(this.personService.get(id)).willReturn(this.earlyAlertCreatorPerson);
	}

	protected void addEarlyAlertWithCampusForStudent(final Person student, final Campus campus) throws Exception {
		this.addEarlyAlertForStudent(student, campus);
	}

	protected void addEarlyAlertWithNoCampusForStudent(final Person student) throws Exception {
		this.addEarlyAlertForStudent(student, null);
	}

	protected void addEarlyAlertForStudent(final Person person, final Campus campus) throws Exception {
		final UUID id = UUID.randomUUID();
		final EarlyAlert earlyAlert = mock(EarlyAlert.class);
		given(earlyAlert.getId()).willReturn(id);
		given(earlyAlert.getPerson()).willReturn(person);
		given(earlyAlert.getCreatedBy()).willReturn(this.earlyAlertCreatorAuditPerson);
		given(earlyAlert.getCreatedDate()).willReturn(DateUtils.addDays(new Date(), -3));
		given(earlyAlert.getCampus()).willReturn(campus);
		this.earlyAlerts.add(earlyAlert);
		given(this.earlyAlertDao.get(id)).willReturn(earlyAlert);
	}

	protected void campusHasNoEacId(final Campus campus) {
		given(campus.getEarlyAlertCoordinatorId()).willReturn(null);
	}

	protected void campusHasInvalidEacId(final Campus campus, final UUID eacUuid) throws Exception {
		given(campus.getEarlyAlertCoordinatorId()).willReturn(eacUuid);
		given(this.personService.get(eacUuid)).willReturn(null);
	}

	protected void campusHasValidEacId(final Campus campus, final UUID eacUuid, final Person eac) throws Exception {
		given(campus.getEarlyAlertCoordinatorId()).willReturn(eacUuid);
		given(this.personService.get(eacUuid)).willReturn(eac);
	}

	protected Person createMockPerson() {
		final UUID id = UUID.randomUUID();
		final Person person = mock(Person.class);
		given(person.getId()).willReturn(id);
		return person;
	}

	protected Person createMockStudentWithNoCoach() throws Exception {
		return this.createMockStudentWithCoach(null);
	}

	protected Person createMockStudentWithCoach(final Person coach) throws Exception {
		final UUID id = UUID.randomUUID();
		final Person student = mock(Person.class);
		given(student.getId()).willReturn(id);
		given(student.getCoach()).willReturn(coach);
		given(this.personService.get(id)).willReturn(student);
		return student;
	}

}
