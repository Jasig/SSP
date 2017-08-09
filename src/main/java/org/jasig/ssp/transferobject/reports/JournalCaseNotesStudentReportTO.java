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
package org.jasig.ssp.transferobject.reports;

import org.jasig.ssp.model.Person;

public class JournalCaseNotesStudentReportTO extends BaseStudentReportTO {
	
	public JournalCaseNotesStudentReportTO(){
		super();
	}
	
	public JournalCaseNotesStudentReportTO(BaseStudentReportTO person)
	{
		super(person);
		caseNoteEntries = 0L;
	}
	
	public JournalCaseNotesStudentReportTO(final Person model) {
		super(model);
		setCreatedDate(model.getCreatedDate());
		caseNoteEntries = 0L;
	}

	private static final long serialVersionUID = 7431422387894606843L;
	
	Long caseNoteEntries;

	public Long getCaseNoteEntries() {
		return caseNoteEntries;
	}
	public void setCaseNoteEntries(Long caseNoteEntries) {
		this.caseNoteEntries = caseNoteEntries;
	}

	String journalSourceName;

	public String getJournalSourceName() {
		return journalSourceName;
	}

	public void setJournalSourceName(String journalSourceName) {
		this.journalSourceName = journalSourceName;
	}
}
