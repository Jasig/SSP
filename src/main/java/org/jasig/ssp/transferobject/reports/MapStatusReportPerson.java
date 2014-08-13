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

import java.util.UUID;

public class MapStatusReportPerson {

	private UUID planId;
	private UUID personId;
	private String schoolId;
	private String programCode;
	private String firstName;
	private String lastName;
	private String catalogYearCode;
	private UUID coachId;
	private UUID ownerId;
	private UUID watcherId;
	
	/**
	 * 
	 */
	public MapStatusReportPerson() {
		super();
	}

	public MapStatusReportPerson(UUID planId, UUID personId, String schoolId, String programCode,String catalogYearCode,
								 String firstName, String lastName, UUID coachId, UUID ownerId) {
		super();
		this.planId = planId;
		this.personId = personId;
		this.schoolId = schoolId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.programCode = programCode;
		this.catalogYearCode = catalogYearCode;
		this.coachId = coachId;
		this.ownerId = ownerId;
	}
	public MapStatusReportPerson(UUID planId, UUID personId, String schoolId, String programCode,String catalogYearCode,
			 String firstName, String lastName, UUID coachId, UUID ownerId, UUID watcherId) {
		super();
		this.planId = planId;
		this.personId = personId;
		this.schoolId = schoolId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.programCode = programCode;
		this.catalogYearCode = catalogYearCode;
		this.coachId = coachId;
		this.ownerId = ownerId;
		this.watcherId = watcherId;
}
	public UUID getPlanId() {
		return planId;
	}

	public void setPlanId(UUID planId) {
		this.planId = planId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCatalogYearCode() {
		return catalogYearCode;
	}

	public void setCatalogYearCode(String catalogYearCode) {
		this.catalogYearCode = catalogYearCode;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public UUID getWatcherId() {
		return watcherId;
	}

	public void setWatcherId(UUID watcherId) {
		this.watcherId = watcherId;
	}

}
