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

import org.junit.Test;

/**
 * JUnit test class for {@link EarlyAlertServiceImpl} sendAllEarlyAlertReminderNotifications method when config 
 * specifies that only coaches should receive the reminders.
 */
public class SendAllEarlyAlertReminderNotificationsToCoachOnlyTest 
		extends SendAllEarlyAlertReminderNotificationsTestBase {

	@Override
	protected void additionalSetUp() throws Exception {
		this.setUpConfig();
	}

	@Override
	protected void additionalTearDown() throws Exception {
	}

	@Test
	public void verifyThatEmailIsSentToCoachWhenStudentHasCoachAndEarlyAlertHasNoCampus() throws Exception {
		//given
		this.studentHasCoachAndEarlyAlertHasNoCampusScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyMessageSentToCoach();
	}

	@Test
	public void verifyThatEmailIsSentToCoachWhenStudentHasCoachAndEarlyAlertCampusHasNoEacId() throws Exception {
		//given
		this.studentHasCoachAndEarlyAlertCampusHasNoEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyMessageSentToCoach();
	}

	@Test
	public void verifyThatEmailIsSentToCoachWhenStudentHasCoachAndEarlyAlertCampusHasInvalidEacId() throws Exception {
		//given
		this.studentHasCoachAndEarlyAlertCampusHasInvalidEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyMessageSentToCoach();
	}

	@Test
	public void verifyThatEmailIsSentToCoachWhenStudentHasCoachAndEarlyAlertCampusHasValidEacId() throws Exception {
		//given
		this.studentHasCoachAndEarlyAlertCampusHasValidEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyMessageSentToCoach();
	}

	@Test
	public void verifyThatNoEmailIsSentToEacWhenStudentHasCoachAndEarlyAlertCampusHasValidEacId() throws Exception {
		//given
		this.studentHasCoachAndEarlyAlertCampusHasValidEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyNoMessageSentToEarlyAlertCoordinator();
	}

	@Test
	public void verifyThatNoEmailIsSentWhenStudentHasNoCoachAndEarlyAlertHasNoCampus() throws Exception {
		// given
		this.studentHasNoCoachAndEarlyAlertHasNoCampusScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyNoMessagesSent();
	}

	@Test
	public void verifyThatNoEmailIsSentWhenStudentHasNoCoachAndEarlyAlertCampusHasNoEacId() throws Exception {
		// given
		this.studentHasNoCoachAndEarlyAlertCampusHasNoEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyNoMessagesSent();
	}

	@Test
	public void verifyThatNoEmailIsSentWhenStudentHasNoCoachAndEarlyAlertCampusHasInvalidEacId() throws Exception {
		// given
		this.studentHasNoCoachAndEarlyAlertCampusHasInvalidEacIdScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyNoMessagesSent();
	}

	@Test
	public void verifyThatNoEmailIsSentToEacWhenStudentHasNoCoachAndEarlyAlertCampusHasValidEacId() throws Exception {
		// given
		this.studentHasNoCoachAndEarlyAlertCampusHasValidEacScenario();
		// when
		this.earlyAlertReminderNotificationsAreTriggered();
		// then
		this.verifyNoMessageSentToEarlyAlertCoordinator();
	}

	private void setUpConfig() {
		given(this.config.includeCoachAsRecipient()).willReturn(true);
		given(this.config.includeEarlyAlertCoordinatorAsRecipient()).willReturn(false);
		given(this.config.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach()).willReturn(false);
	}

}
