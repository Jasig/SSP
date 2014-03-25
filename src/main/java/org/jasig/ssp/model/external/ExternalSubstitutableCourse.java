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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_substitutable_course")
public class ExternalSubstitutableCourse  implements ExternalData, Serializable {

	private static final long serialVersionUID = -3772409086449744611L;

	@Column(nullable = false, length = 25)
	private String termCode;
	
	@Column(nullable = false, length = 50)
	private String programCode;

	@Column(nullable = false, length = 35)
	private String sourceFormattedCourse;	

	@Column(nullable = false, length = 50)
	private String sourceCourseCode;	

	@Column(nullable = false, length = 50)
	private String sourceCourseTitle;	
	
	@Column
	private BigDecimal sourceCreditHours;
	
	@Column
	private BigDecimal targetCreditHours;
	
	@Column(nullable = false, length = 35)
	private String targetFormattedCourse;	

	@Column(nullable = false, length = 50)
	private String targetCourseCode;	

	@Column(nullable = false, length = 50)
	private String targetCourseTitle;
	
	@Id
	@Type(type = "string")
	private String id;


	public String getSourceFormattedCourse() {
		return sourceFormattedCourse;
	}

	public void setSourceFormattedCourse(String sourceFormattedCourse) {
		this.sourceFormattedCourse = sourceFormattedCourse;
	}

	public String getSourceCourseCode() {
		return sourceCourseCode;
	}

	public void setSourceCourseCode(String sourceCourseCode) {
		this.sourceCourseCode = sourceCourseCode;
	}

	public String getSourceCourseTitle() {
		return sourceCourseTitle;
	}

	public void setSourceCourseTitle(String sourceCourseTitle) {
		this.sourceCourseTitle = sourceCourseTitle;
	}

	public String getTargetFormattedCourse() {
		return targetFormattedCourse;
	}

	public void setTargetFormattedCourse(String targetFormattedCourse) {
		this.targetFormattedCourse = targetFormattedCourse;
	}

	public String getTargetCourseCode() {
		return targetCourseCode;
	}

	public void setTargetCourseCode(String targetCourseCode) {
		this.targetCourseCode = targetCourseCode;
	}

	public String getTargetCourseTitle() {
		return targetCourseTitle;
	}

	public void setTargetCourseTitle(String targetCourseTitle) {
		this.targetCourseTitle = targetCourseTitle;
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = id.toString();
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public BigDecimal getSourceCreditHours() {
		return sourceCreditHours;
	}

	public void setSourceCreditHours(BigDecimal sourceCreditHours) {
		this.sourceCreditHours = sourceCreditHours;
	}

	public BigDecimal getTargetCreditHours() {
		return targetCreditHours;
	}

	public void setTargetCreditHours(BigDecimal targetCreditHours) {
		this.targetCreditHours = targetCreditHours;
	}	
}
