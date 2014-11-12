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
package org.jasig.ssp.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

import org.jasig.ssp.service.reference.ConfigService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * JUnit test class for {@link EarlyAlertResponseReminderRecipientsConfigImpl}.
 */
public class EarlyAlertResponseReminderRecipientsConfigImplTest {

	@Mock private ConfigService configService;

	@InjectMocks private EarlyAlertResponseReminderRecipientsConfigImpl impl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	// includeCoachAsRecipient()

	@Test
	public void includeCoachAsRecipientMethodShouldReturnTrueForCoachAndEacConfigValue() {
		this.configValueIsCoachAndEac();
		assertTrue(this.impl.includeCoachAsRecipient());
	}

	@Test
	public void includeCoachAsRecipientMethodShouldReturnTrueForCoachOnlyConfigValue() {
		this.configValueIsCoachOnly();
		assertTrue(this.impl.includeCoachAsRecipient());
	}

	@Test
	public void includeCoachAsRecipientMethodShouldReturnTrueForCoachOnlyOrEacIfNoCoachConfigValue() {
		this.configValueIsCoachOnlyOrEacIfNoCoach();
		assertTrue(this.impl.includeCoachAsRecipient());
	}

	@Test
	public void includeCoachAsRecipientMethodShouldReturnFalseForEacOnlyConfigValue() {
		this.configValueIsEacOnly();
		assertFalse(this.impl.includeCoachAsRecipient());
	}

	@Test
	public void includeCoachAsRecipientMethodShouldReturnTrueForInvalidConfigValue() {
		this.configValueIsInvalid();
		assertTrue(this.impl.includeCoachAsRecipient());
	}

	// includeEarlyAlertCoordinatorAsRecipient()

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientMethodShouldReturnTrueForCoachAndEacConfigValue() {
		this.configValueIsCoachAndEac();
		assertTrue(this.impl.includeEarlyAlertCoordinatorAsRecipient());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientMethodShouldReturnFalseForCoachOnlyConfigValue() {
		this.configValueIsCoachOnly();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipient());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientMethodShouldReturnFalseForCoachOnlyOrEacIfNoCoachConfigValue() {
		this.configValueIsCoachOnlyOrEacIfNoCoach();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipient());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientMethodShouldReturnTrueForEacOnlyConfigValue() {
		this.configValueIsEacOnly();
		assertTrue(this.impl.includeEarlyAlertCoordinatorAsRecipient());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientMethodShouldReturnFalseForInvalidConfigValue() {
		this.configValueIsInvalid();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipient());
	}

	// includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach()

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoachMethodShouldReturnFalseForCoachAndEacConfigValue() {
		this.configValueIsCoachAndEac();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoachMethodShouldReturnFalseForCoachOnlyConfigValue() {
		this.configValueIsCoachOnly();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoachMethodShouldReturnTrueForCoachOnlyOrEacIfNoCoachConfigValue() {
		this.configValueIsCoachOnlyOrEacIfNoCoach();
		assertTrue(this.impl.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoachMethodShouldReturnFalseForEacOnlyConfigValue() {
		this.configValueIsEacOnly();
		assertFalse(this.impl.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach());
	}

	@Test
	public void includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoachMethodShouldReturnTrueForInvalidConfigValue() {
		this.configValueIsInvalid();
		assertTrue(this.impl.includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach());
	}

	// helper methods

	private void configValueIsCoachOnly() {
		given(this.configService.getByNameNullOrDefaultValue(anyString())).willReturn(EarlyAlertResponseReminderRecipientsConfigImpl.COACH_ONLY);
	}

	private void configValueIsCoachOnlyOrEacIfNoCoach() {
		given(this.configService.getByNameNullOrDefaultValue(anyString())).willReturn(EarlyAlertResponseReminderRecipientsConfigImpl.COACH_ONLY_OR_EAC_IF_NO_COACH);
	}

	private void configValueIsEacOnly() {
		given(this.configService.getByNameNullOrDefaultValue(anyString())).willReturn(EarlyAlertResponseReminderRecipientsConfigImpl.EAC_ONLY);
	}

	private void configValueIsCoachAndEac() {
		given(this.configService.getByNameNullOrDefaultValue(anyString())).willReturn(EarlyAlertResponseReminderRecipientsConfigImpl.COACH_AND_EAC);
	}

	private void configValueIsInvalid() {
		given(this.configService.getByNameNullOrDefaultValue(anyString())).willReturn("invalid_value");
	}

}
