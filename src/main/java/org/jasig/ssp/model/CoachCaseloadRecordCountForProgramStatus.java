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
package org.jasig.ssp.model;

import java.util.UUID;

import org.jasig.ssp.model.reference.ProgramStatus;

/**
 * Denormalized and calculated view onto a coach's caseload for a particular
 * {@link ProgramStatus}.
 *
 * <p>Serves a simlar function to {@link CaseloadRecord}. Sort of a summative
 * view of those records on a per-coach, per-status basis. Also, like
 * {@link CaseloadRecord}, this is intentionally not a proper-JPA entity, but a
 * projection-derived pseudo-entity that exists primarily in support of
 * reporting requirements.</p>
 *
 */
public class CoachCaseloadRecordCountForProgramStatus {

	private UUID coachId;

	private String coachUsername;

	private String coachSchoolId;

	private String coachFirstName;

	private String coachMiddleName;

	private String coachLastName;

	private String coachDepartmentName;

	private UUID programStatusId;

	private long count;

	public CoachCaseloadRecordCountForProgramStatus() {}
	public CoachCaseloadRecordCountForProgramStatus(UUID coachId,
													UUID programStatusId) {
		this(coachId, programStatusId, 0);
	}
	public CoachCaseloadRecordCountForProgramStatus(UUID coachId,
													UUID programStatusId,
													long count) {
		this(coachId, programStatusId, count, null,  null, null, null, null, null);
	}
	public CoachCaseloadRecordCountForProgramStatus(UUID coachId,
													UUID programStatusId,
													long count,
													String coachUsername,
													String coachSchoolId,
													String coachFirstName,
													String coachMiddleName,
													String coachLastName,
													String coachDepartmentName) {
		this.coachId = coachId;
		this.programStatusId = programStatusId;
		this.count = count;
		this.coachUsername = coachUsername;
		this.coachSchoolId = coachSchoolId;
		this.coachFirstName = coachFirstName;
		this.coachMiddleName = coachMiddleName;
		this.coachLastName = coachLastName;
		this.coachDepartmentName = coachDepartmentName;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public String getCoachUsername() {
		return coachUsername;
	}

	public void setCoachUsername(String coachUsername) {
		this.coachUsername = coachUsername;
	}

	public String getCoachSchoolId() {
		return coachSchoolId;
	}

	public void setCoachSchoolId(String coachSchoolId) {
		this.coachSchoolId = coachSchoolId;
	}

	public String getCoachFirstName() {
		return coachFirstName;
	}

	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}

	public String getCoachMiddleName() {
		return coachMiddleName;
	}

	public void setCoachMiddleName(String coachMiddleName) {
		this.coachMiddleName = coachMiddleName;
	}

	public String getCoachLastName() {
		return coachLastName;
	}

	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}

	public String getCoachDepartmentName() {
		return coachDepartmentName;
	}

	public void setCoachDepartmentName(String coachDepartmentName) {
		this.coachDepartmentName = coachDepartmentName;
	}

	public UUID getProgramStatusId() {
		return programStatusId;
	}

	public void setProgramStatus(UUID programStatusId) {
		this.programStatusId = programStatusId;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * Collects all fields into a String representation. Note that
	 * {@link #equals(Object)} abd {@link #hashCode()} only consider UUIDs. So
	 * two instances with different {@link #toString()} results
	 * could be considered equal or might hash identically.
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "CoachCaseloadRecordCountForProgramStatus{" +
				"coachId=" + coachId +
				", coachUsername='" + coachUsername + '\'' +
				", coachSchoolId='" + coachSchoolId + '\'' +
				", coachFirstName='" + coachFirstName + '\'' +
				", coachMiddleName='" + coachMiddleName + '\'' +
				", coachLastName='" + coachLastName + '\'' +
				", coachDepartmentName='" + coachDepartmentName + '\'' +
				", programStatusId=" + programStatusId +
				", count=" + count +
				'}';
	}

	/**
	 * This class is a view onto entities rather than an entity itself, so
	 * might be more properly considered a value object, but the reality is that
	 * the two UUID fields act as a compound primary key so we just compare
	 * those when testing equality. Client code or new code in this class would
	 * be needed to compare counts and/or other calculated/non-UUID fields.
	 *
	 * <p>Use {@link #equalsAllFields(Object)} for comparing equality across
	 * all fields, not just domain identities.</p>
	 *
	 * <p>Note that two instances with different {@link #toString()} results
	 * could be considered equal.</p>
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CoachCaseloadRecordCountForProgramStatus))
			return false;

		CoachCaseloadRecordCountForProgramStatus that = (CoachCaseloadRecordCountForProgramStatus) o;

		if (coachId != null ? !coachId.equals(that.coachId) : that.coachId != null)
			return false;
		if (programStatusId != null ? !programStatusId.equals(that.programStatusId) : that.programStatusId != null)
			return false;

		return true;
	}


	/**
	 * Compare state of all fields, not just the domain identity fields
	 * compared by {@link #equals(Object)}. Mainly useful for testing.
	 *
	 * @param o
	 * @return
	 */
	public boolean equalsAllFields(Object o) {
		if (this == o) return true;
		if (!(o instanceof CoachCaseloadRecordCountForProgramStatus))
			return false;

		CoachCaseloadRecordCountForProgramStatus that = (CoachCaseloadRecordCountForProgramStatus) o;

		if (count != that.count) return false;
		if (coachDepartmentName != null ? !coachDepartmentName.equals(that.coachDepartmentName) : that.coachDepartmentName != null)
			return false;
		if (coachFirstName != null ? !coachFirstName.equals(that.coachFirstName) : that.coachFirstName != null)
			return false;
		if (coachId != null ? !coachId.equals(that.coachId) : that.coachId != null)
			return false;
		if (coachLastName != null ? !coachLastName.equals(that.coachLastName) : that.coachLastName != null)
			return false;
		if (coachMiddleName != null ? !coachMiddleName.equals(that.coachMiddleName) : that.coachMiddleName != null)
			return false;
		if (coachSchoolId != null ? !coachSchoolId.equals(that.coachSchoolId) : that.coachSchoolId != null)
			return false;
		if (coachUsername != null ? !coachUsername.equals(that.coachUsername) : that.coachUsername != null)
			return false;
		if (programStatusId != null ? !programStatusId.equals(that.programStatusId) : that.programStatusId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = coachId != null ? coachId.hashCode() : 0;
		result = 31 * result + (programStatusId != null ? programStatusId.hashCode() : 0);
		return result;
	}
}
