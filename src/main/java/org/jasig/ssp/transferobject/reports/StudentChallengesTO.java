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

import java.util.UUID;

public class StudentChallengesTO {

	UUID id;
	String schoolId;
	String lastName;
	String firstName;
	String studentType;
	String coachName;
	String challengeName;
	String coachLastName;
	String coachFirstName;
	String homeCampusName;
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getStudentType() {
		return studentType;
	}
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}
	public String getCoachName(){
		if(coachName == null || coachName.length() <= 0)
			coachName = getCoachFirstName() + " " + getCoachLastName();
		return coachName;
	}
	public String getCoachLastName() {
		return coachLastName;
	}
	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}
	public String getCoachFirstName() {
		return coachFirstName;
	}
	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getChallengeName() {
		return challengeName;
	}
	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public String getHomeCampusName() {
		return homeCampusName;
	}

	public void setHomeCampusName(String homeCampusName) {
		this.homeCampusName = homeCampusName;
	}
}
