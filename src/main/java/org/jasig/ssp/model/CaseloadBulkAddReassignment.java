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
package org.jasig.ssp.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
//@Table(name = "caseload_bulk_add_reassignment")
public class CaseloadBulkAddReassignment implements Serializable {

	private static final long serialVersionUID = 8949032547776456961L;

	@Id
	@Column(length = 50)
	@Size(max = 50)
	private String schoolId;

	@Id
	@Column(length = 50)
	@Size(max = 50)
	private String coachSchoolId;

	@Column(length = 50)
	@Size(max = 50)
	private String modifiedBySchoolId;

	@Column(length = 1073741823)
	@Size(max = 1073741823)
	private String journalEntryComment;

	@Column(length = 100)
	@Size(max = 100)
	private String notificationEmailAddress;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getCoachSchoolId() {
		return coachSchoolId;
	}

	public void setCoachSchoolId(String coachSchoolId) {
		this.coachSchoolId = coachSchoolId;
	}

	public String getModifiedBySchoolId() {
		return modifiedBySchoolId;
	}

	public void setModifiedBySchoolId(String modifiedBySchoolId) {
		this.modifiedBySchoolId = modifiedBySchoolId;
	}

	public String getJournalEntryComment() {
		return journalEntryComment;
	}

	public void setJournalEntryComment(String journalEntryComment) {
		this.journalEntryComment = journalEntryComment;
	}

	public String toString() {
		return "Student School Id: \"" + schoolId + "\" Coach School Id: \"" + coachSchoolId + "\" Modified By School Id: \"" + modifiedBySchoolId + "\"";
	}

	public String getNotificationEmailAddress() {
		return notificationEmailAddress;
	}

	public void setNotificationEmailAddress(String notificationEmailAddress) {
		this.notificationEmailAddress = notificationEmailAddress;
	}

	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		CaseloadBulkAddReassignment caseloadBulkAddReassignment = (CaseloadBulkAddReassignment) obj;
		return StringUtils.equals(getSchoolId(), caseloadBulkAddReassignment.getSchoolId()) &&
				StringUtils.equals(getCoachSchoolId(), caseloadBulkAddReassignment.getCoachSchoolId()) &&
				StringUtils.equals(getModifiedBySchoolId(), caseloadBulkAddReassignment.getModifiedBySchoolId());
	}
}
