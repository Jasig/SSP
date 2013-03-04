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
package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_course_section")
public class ExternalCourseSection extends AbstractExternalData implements
		ExternalData, Serializable {
	/*formatted_course	varchar(35)	No
	subject_abbreviation	varchar(10)	No
	number	varchar(15)	No
	section_number	varchar(10)	No
	credit_value	decimal(9,2)	No
	term_code	varchar(25)	No
	description	varchar(2500)	Yes*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5577083362347693294L;

	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String formattedCourse;
	
	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String subjectAbbreviation;
	
	@Column(nullable = false, length = 15)
	@NotNull
	@NotEmpty
	@Size(max = 15)
	private String number;
	
	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String sectionNumber;
	
	private BigDecimal creditValue;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;
	
	@Column(nullable = false, length = 2500)
	@NotNull
	@NotEmpty
	@Size(max = 2500)
	private String description;

	/**
	 * @return the formattedCourse
	 */
	public String getFormattedCourse() {
		return formattedCourse;
	}

	/**
	 * @param formattedCourse the formattedCourse to set
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	/**
	 * @return the subjectAbbreviation
	 */
	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}

	/**
	 * @param subjectAbbreviation the subjectAbbreviation to set
	 */
	public void setSubjectAbbreviation(final String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(final String number) {
		this.number = number;
	}

	/**
	 * @return the sectionNumber
	 */
	public String getSectionNumber() {
		return sectionNumber;
	}

	/**
	 * @param sectionNumber the sectionNumber to set
	 */
	public void setSectionNumber(final String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	/**
	 * @return the creditValue
	 */
	public BigDecimal getCreditValue() {
		return creditValue;
	}

	/**
	 * @param creditValue the creditValue to set
	 */
	public void setCreditValue(final BigDecimal creditValue) {
		this.creditValue = creditValue;
	}

	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

}
