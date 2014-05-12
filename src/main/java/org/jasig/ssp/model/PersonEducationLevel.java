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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.reference.EducationLevel;

/**
 * Students may have zero or multiple Education Levels.
 * 
 * The PersonEducationLevel entity is an associative mapping between a student
 * (Person) and any Education Level information about them.
 * 
 * Non-student users should never have any assigned Education Levels.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationLevel // NOPMD by jon.adams on 5/24/12 1:34 PM
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -7969723552077396105L;

	@Column(length = 255)
	@Size(max = 255)
	private String description;
	
	@Column(length = 20)
	@Size(max = 20)
	private String lastYearAttended;

	@Column(length = 10)
	@Size(max = 10)
	private String highestGradeCompleted;

	@Column(length = 20)
	@Size(max = 20)
	private String graduatedYear;

	@Column(length = 255)
	@Size(max = 255)
	private String schoolName;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_level_id", updatable = false, nullable = false)
	private EducationLevel educationLevel;

	public PersonEducationLevel() {
		super();
	}

	public PersonEducationLevel(final Person person,
			final EducationLevel educationLevel) {
		super();
		this.person = person;
		this.educationLevel = educationLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLastYearAttended() {
		return lastYearAttended;
	}

	public void setLastYearAttended(final String lastYearAttended) {
		this.lastYearAttended = lastYearAttended;
	}

	public String getHighestGradeCompleted() {
		return highestGradeCompleted;
	}

	public void setHighestGradeCompleted(final String highestGradeCompleted) {
		this.highestGradeCompleted = highestGradeCompleted;
	}

	public String getGraduatedYear() {
		return graduatedYear;
	}

	public void setGraduatedYear(final String graduatedYear) {
		this.graduatedYear = graduatedYear;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(final EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	@Override
	protected int hashPrime() {
		return 17;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:10 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonEducationLevel
		result *= hashField("description", description);
		result *= hashField("lastYearAttended", lastYearAttended);
		result *= hashField("highestGradeCompleted", highestGradeCompleted);
		result *= hashField("graduatedYear", graduatedYear);
		result *= hashField("schoolName", schoolName);
		result *= hashField("person", person);
		result *= hashField("educationLevel", educationLevel);

		return result;
	}
}