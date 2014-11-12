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

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.service.reference.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class used to determine the recipients for Early Alert Response reminders.  Uses ConfigService and the "ear_reminder_recipients" config.
 */
@Component
public class EarlyAlertResponseReminderRecipientsConfigImpl implements EarlyAlertResponseReminderRecipientsConfig {

	public static final String CONFIG_NAME = "ear_reminder_recipients";
	public static final String COACH_AND_EAC = "coach_and_eac";
	public static final String COACH_ONLY = "coach_only";
	public static final String COACH_ONLY_OR_EAC_IF_NO_COACH = "coach_only_or_eac_if_no_coach";
	public static final String EAC_ONLY = "eac_only";
	public static final List<String> VALID_VALUES = new ArrayList<String>(5);

	static {
		VALID_VALUES.add(COACH_AND_EAC);
		VALID_VALUES.add(COACH_ONLY);
		VALID_VALUES.add(COACH_ONLY_OR_EAC_IF_NO_COACH);
		VALID_VALUES.add(EAC_ONLY);
	}

	@Autowired
	private transient ConfigService configService;

	private String defaultValue = COACH_ONLY_OR_EAC_IF_NO_COACH;

	/**
	 * @see org.jasig.ssp.config.EarlyAlertResponseReminderRecipientsConfig#includeCoachAsRecipient()
	 */
	@Override
	public boolean includeCoachAsRecipient() {
		final String valueToUse = this.getConfigValueToUse();
		return COACH_ONLY.equals(valueToUse) || COACH_ONLY_OR_EAC_IF_NO_COACH.equals(valueToUse) || COACH_AND_EAC.equals(valueToUse);
	}

	/**
	 * @see org.jasig.ssp.config.EarlyAlertResponseReminderRecipientsConfig#includeEarlyAlertCoordinatorAsRecipient()
	 */
	@Override
	public boolean includeEarlyAlertCoordinatorAsRecipient() {
		final String valueToUse = this.getConfigValueToUse();
		return EAC_ONLY.equals(valueToUse) || COACH_AND_EAC.equals(valueToUse);
	}

	/**
	 * @see org.jasig.ssp.config.EarlyAlertResponseReminderRecipientsConfig#includeEarlyAlertCoordinatorAsRecipientOnlIfStudentHasNoCoach()
	 */
	@Override
	public boolean includeEarlyAlertCoordinatorAsRecipientOnlyIfStudentHasNoCoach() {
		final String valueToUse = this.getConfigValueToUse();
		return COACH_ONLY_OR_EAC_IF_NO_COACH.equals(valueToUse);
	}

	private String getConfigValueToUse() {
		String result = this.getConfigValue();
		if (!VALID_VALUES.contains(result)) {
			result = this.defaultValue;
		}
		return result;
	}

	private String getConfigValue() {
		return this.configService.getByNameNullOrDefaultValue(COACH_ONLY_OR_EAC_IF_NO_COACH);
	}

}
