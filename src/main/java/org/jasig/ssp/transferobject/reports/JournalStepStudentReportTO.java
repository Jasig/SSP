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

public class JournalStepStudentReportTO extends BaseStudentReportTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054716071286423025L;
	String journalStepDetailName;

	/**
	 * 
	 */
	public JournalStepStudentReportTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param person
	 */
	public JournalStepStudentReportTO(BaseStudentReportTO person) {
		super(person);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the journalStepName
	 */
	public String getJournalStepDetailName() {
		return journalStepDetailName;
	}

	/**
	 * @param journalStepName the journalStepName to set
	 */
	public void setJournalStepDetailName(String journalStepDetailName) {
		this.journalStepDetailName = journalStepDetailName;
	}
}
