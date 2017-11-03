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
package org.jasig.ssp.model.reference;

import org.jasig.ssp.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/*
 * Allows e-mails and other messaging to be customized using the Velocity Framework
 */
@Entity
public class MessageTemplate
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 2756578010766636880L;

	// Message Templates

	public static final UUID BULK_ADD_CASELOAD_REASSIGNMENT_ID = UUID
			.fromString("7df2c7bb-3c9d-435a-87f1-501e43bda153");

	public static final UUID JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-08eb4f7ee2f9");

	public static final UUID EARLYALERT_MESSAGETOSTUDENT_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-08eb4f8ef40e");

	public static final UUID EARLYALERT_CONFIRMATIONTOADVISOR_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-08eb4f7ee3fc");

	public static final UUID EARLYALERT_CONFIRMATIONTOFACULTY_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-09eb5f8ef55f");

	public static final UUID EARLYALERT_RESPONSETOFACULTYFROMCOACH_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-09fb5f9ef680");

	public static final UUID CUSTOM_ACTION_PLAN_TASK_ID = UUID
			.fromString("31CF8D8D-2BC9-44E0-AAD1-D8BA43530BB0");

	public static final UUID ACTION_PLAN_STEP_ID = UUID
			.fromString("AEC07252-1FF0-479D-A2EF-C0E017E1C05D");

	public static final UUID CONTACT_COACH_ID = UUID
			.fromString("0B7E484D-44E4-4F0D-8DB5-3518D015B495");

	public static final UUID ACTION_PLAN_EMAIL_ID = UUID
			.fromString("5D183F35-023D-40EA-B8D9-66FBE190FFFB");

	public static final UUID NEW_STUDENT_INTAKE_TASK_EMAIL_ID = UUID
			.fromString("9D3CE5B1-E27D-40C8-8F45-ABCB1BCCF3B0");
	
	public static final UUID OUTPUT_MAP_PLAN_MATRIX_ID = UUID
			.fromString("aa2e0356-46df-4acd-ab3e-b96a6aa943d3");

	public static final UUID OUTPUT_MAP_PLAN_SHORT_MATRIX_ID = UUID
			.fromString("f66d95ed-7696-4a0d-bcd5-7997d73fa972");

	public static final UUID OUTPUT_TEMPLATE_PLAN_MATRIX_ID = UUID
			.fromString("bcc180b0-3a8b-11e3-aa6e-0800200c9a66");
	
	public static final UUID OUTPUT_MAP_PLAN_FULL_ID = UUID
			.fromString("df47a4b0-b666-11e2-9e96-0800200c9a66");
	
	public static final UUID MAP_STATUS_REPORT_ID = UUID
			.fromString("3da780e7-b7df-43b2-8988-fc51ef03f7c0");
	
	public static final UUID EARLYALERT_RESPONSE_TO_REFERRAL_SOURCE_ID = UUID.
			fromString("d6d1f68a-0533-426f-bd0b-d129a92edf81");
	
	public static final UUID EARLYALERT_RESPONSE_REQUIRED_ID = UUID.
			fromString("bfae36f8-53d0-486f-8bc5-a9d98beb544e");

    public static final UUID MYGPS_WELCOME_MESSAGE_ID = UUID.
            fromString("bfae38f8-53b0-486f-8bc5-a9d99beb514e");
    
    public static final UUID EMAIL_JOURNAL_ENTRY_ID = UUID.
    		fromString("65bb389e-8c4f-4a7e-adc2-0938b1e66abe");

	public static final UUID EMAIL_COACHING_ASSIGNMENT_CHANGE_ID = UUID.
			fromString("b0e61fad-74c2-4154-9248-7ca8db867fdf");

	public static final UUID EMAIL_SPECIAL_SERVICE_GROUP_COURSE_WITHDRAWAL_TO_ADVISOR_ID = UUID.
			fromString("55185f25-3356-465e-9ebc-c62125bd7ca2");

	@NotNull
	@Column(name = "subject", nullable = false, length = 250)
	private String subject;

	@NotNull
	@Column(name = "body", nullable = false, columnDefinition = "text")
	private String body;

	/**
	 * Empty constructor
	 */
	public MessageTemplate() {
		super();
	}

	public MessageTemplate(final UUID id) {
		super(id);
	}

	public MessageTemplate(final UUID id, final String name) {
		super(id, name);
	}

	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            Maximum length of 250 characters.
	 */
	public void setSubject(@NotNull final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(@NotNull final String body) {
		this.body = body;
	}

	/**
	 * Get a cacheable id for the subject template
	 * 
	 * @return A cacheable id for the subject template
	 */
	public String subjectTemplateId() {
		return getId().toString() + "-s-" + getModifiedDate().getTime();
	}

	/**
	 * Get a cacheable id for the body template
	 * 
	 * @return A cacheable id for the body template
	 */
	public String bodyTemplateId() {
		return getId().toString() + "-b-" + getModifiedDate().getTime();
	}

	@Override
	protected int hashPrime() {
		return 113;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= super.hashCode();

		result *= hashField("subject", subject);
		result *= hashField("body", body);

		return result;
	}
}