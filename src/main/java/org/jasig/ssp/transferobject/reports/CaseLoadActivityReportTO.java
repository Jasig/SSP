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
package org.jasig.ssp.transferobject.reports;

public class CaseLoadActivityReportTO {

	final String firstName;
	final String lastName;

	final Long journalEntriesCreatedCount;
	final Long studentsJournalEntriesCount;
	final Long actionPlanTasksCreatedCount;
	final Long studentsActionPlanTasksCount;
	final Long earlyAlertsCreatedCount;
	final Long studentsEarlyAlertsCount;
	final Long earlyAlertsRespondedCount;
	
	
	public CaseLoadActivityReportTO(String firstName, String lastName,
			Long journalEntriesCreatedCount, Long studentsJournalEntriesCount,
			Long actionPlanTasksCreatedCount,
			Long studentsActionPlanTasksCount, Long earlyAlertsCreatedCount,
			Long studentsEarlyAlertsCount, Long earlyAlertsRespondedCount) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.journalEntriesCreatedCount = journalEntriesCreatedCount;
		this.studentsJournalEntriesCount = studentsJournalEntriesCount;
		this.actionPlanTasksCreatedCount = actionPlanTasksCreatedCount;
		this.studentsActionPlanTasksCount = studentsActionPlanTasksCount;
		this.earlyAlertsCreatedCount = earlyAlertsCreatedCount;
		this.studentsEarlyAlertsCount = studentsEarlyAlertsCount;
		this.earlyAlertsRespondedCount = earlyAlertsRespondedCount;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Long getJournalEntriesCreatedCount() {
		return journalEntriesCreatedCount;
	}
	public Long getStudentsJournalEntriesCount() {
		return studentsJournalEntriesCount;
	}
	public Long getActionPlanTasksCreatedCount() {
		return actionPlanTasksCreatedCount;
	}
	public Long getStudentsActionPlanTasksCount() {
		return studentsActionPlanTasksCount;
	}
	public Long getEarlyAlertsCreatedCount() {
		return earlyAlertsCreatedCount;
	}
	public Long getStudentsEarlyAlertsCount() {
		return studentsEarlyAlertsCount;
	}
	public Long getEarlyAlertsRespondedCount() {
		return earlyAlertsRespondedCount;
	}
}
