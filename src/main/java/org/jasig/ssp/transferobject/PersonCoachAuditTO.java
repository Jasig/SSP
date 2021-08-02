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
package org.jasig.ssp.transferobject;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


/**
 * Previous Coach Transfer Object
 */
public class PersonCoachAuditTO implements Serializable {

	private static final long serialVersionUID = 2561442272088399L;

	private UUID studentId;

	private PersonLiteTO currentCoach;

	private PersonLiteTO previousCoach;

	private Date modifiedDate;

	private PersonLiteTO modifiedBy;


	public PersonCoachAuditTO () {}

	/**
	 * Constructs a CoachAuditTO. Needs personId and modifiedDate as that's what makes this unique.
	 * @param personId The person id UUID
	 * @param currentCoach The currentCoach
	 * @param previousCoach The previousCoach
	 * @param modifiedDate The date modified
	 * @param modifiedBy The person that modified the data
	 */
	public PersonCoachAuditTO (@NotNull final UUID personId, final PersonLiteTO currentCoach, final PersonLiteTO previousCoach,
							   @NotNull final Date modifiedDate, final PersonLiteTO modifiedBy) {
		this.studentId = personId;
		this.currentCoach = currentCoach;
		this.previousCoach = previousCoach;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	public UUID getStudentId () {
		return studentId;
	}

	public void setStudentId (UUID studentId) {
		this.studentId = studentId;
	}

	public PersonLiteTO getCurrentCoach () {
		return currentCoach;
	}

	public void setCurrentCoach (PersonLiteTO currentCoach) {
		this.currentCoach = currentCoach;
	}

	public PersonLiteTO getPreviousCoach () {
		return previousCoach;
	}

	public void setPreviousCoach (PersonLiteTO previousCoach) {
		this.previousCoach = previousCoach;
	}

	public Date getModifiedDate () {
		return modifiedDate;
	}

	public void setModifiedDate (Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public PersonLiteTO getModifiedBy () {
		return modifiedBy;
	}

	public void setModifiedBy (PersonLiteTO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
