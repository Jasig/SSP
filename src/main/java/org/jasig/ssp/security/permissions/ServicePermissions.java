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
package org.jasig.ssp.security.permissions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * Permissions
 */
public enum ServicePermissions {
	PERSON_APPOINTMENT_READ,
	PERSON_APPOINTMENT_WRITE,
	PERSON_APPOINTMENT_DELETE,
	PERSON_CASELOAD_READ,
	PERSON_CHALLENGE_READ,
	PERSON_CHALLENGE_WRITE,
	PERSON_CHALLENGE_DELETE,
	PERSON_DOCUMENT_READ,
	PERSON_DOCUMENT_WRITE,
	PERSON_DOCUMENT_DELETE,
	PERSON_EARLY_ALERT_READ,
	PERSON_EARLY_ALERT_WRITE,
	PERSON_EARLY_ALERT_DELETE,
	PERSON_EARLY_ALERT_RESPONSE_READ,
	PERSON_EARLY_ALERT_RESPONSE_WRITE,
	PERSON_EARLY_ALERT_RESPONSE_DELETE,
	PERSON_GOAL_READ,
	PERSON_GOAL_WRITE,
	PERSON_GOAL_DELETE,
	PERSON_JOURNAL_ENTRY_READ,
	PERSON_JOURNAL_ENTRY_WRITE,
	PERSON_JOURNAL_ENTRY_DELETE,
	PERSON_READ,
	PERSON_WRITE,
	PERSON_DELETE,
	PERSON_SEARCH_READ,
	PERSON_PROGRAM_STATUS_READ,
	PERSON_PROGRAM_STATUS_WRITE,
	PERSON_PROGRAM_STATUS_DELETE,
	PERSON_TASK_READ,
	PERSON_TASK_WRITE,
	PERSON_TASK_DELETE,
	PERSON_INSTRUCTION_READ,

	REFERENCE_READ,
	REFERENCE_WRITE,

	STUDENT_INTAKE_READ,
	STUDENT_INTAKE_WRITE;

	public String asPermissionString() {
		return "ROLE_" + this;
	}

	public GrantedAuthority asGrantedAuthority() {
		return new GrantedAuthorityImpl(asPermissionString());
	}
}