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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PersonCourseStatus extends AbstractAuditable  implements Auditable {

	private static final long serialVersionUID = 7609991641488688420L;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;
	
	@Column(length = 25, nullable = false)
	@Size(max = 25)
	private String termCode;

	@Column(length = 35, nullable = false)
	@Size(max = 35)
	private String formattedCourse;
	
	@Column(length = 128, nullable = false)
	@Size(max = 128)
	private String sectionCode;

	@Column(length = 2)
	@Size(max = 2)
	private String previousStatusCode;
	
	@Column(length = 2, nullable = false)
	@Size(max = 2)
	private String statusCode;


	@Override
	protected int hashPrime() {
		return 11;
	}

	@Override
	final public int hashCode() {
		int result = hashPrime();
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());
		result *= hashField("person", person);
		result *= hashField("termCode", termCode);
		result *= hashField("formattedCourse", formattedCourse);
		result *= hashField("sectionCode", sectionCode);
        result *= hashField("previousStatusCode", previousStatusCode);
		result *= hashField("statusCode", statusCode);
		return result;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

    public String getPreviousStatusCode() {
        return previousStatusCode;
    }

    public void setPreviousStatusCode(String previousStatusCode) {
        this.previousStatusCode = previousStatusCode;
    }

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}