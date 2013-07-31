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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_course_requisite")
public class ExternalCourseRequisite extends AbstractExternalData implements
		ExternalData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4278973404278554219L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String requiringCourseCode;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String requiredCourseCode;
	
	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String requiredFormattedCourse;


	/**
	 * Requisite Code.
	 * 
	 * Most commonly {@link RequisiteCode#PRE} or
	 * {@link RequisiteCode#CO}, but other enum values possible.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RequisiteCode requisiteCode;


	/**
	 * @return the requiringCourseCode
	 */
	public String getRequiringCourseCode() {
		return requiringCourseCode;
	}


	/**
	 * @param requiringCourseCode the requiringCourseCode to set
	 */
	public void setRequiringCourseCode(String requiringCourseCode) {
		this.requiringCourseCode = requiringCourseCode;
	}


	/**
	 * @return the requiredCourseCode
	 */
	public String getRequiredCourseCode() {
		return requiredCourseCode;
	}


	/**
	 * @param requiredCourseCode the requiredCourseCode to set
	 */
	public void setRequiredCourseCode(String requiredCourseCode) {
		this.requiredCourseCode = requiredCourseCode;
	}


	/**
	 * @return the requisiteCode
	 */
	public RequisiteCode getRequisiteCode() {
		return requisiteCode;
	}


	/**
	 * @param requisiteCode the requisiteCode to set
	 */
	public void setRequisiteCode(RequisiteCode requisiteCode) {
		this.requisiteCode = requisiteCode;
	}


	/**
	 * @return the requiredFormattedCourse
	 */
	public String getRequiredFormattedCourse() {
		return requiredFormattedCourse;
	}


	/**
	 * @param requiredFormattedCourse the requiredFormattedCourse to set
	 */
	public void setRequiredFormattedCourse(String requiredFormattedCourse) {
		this.requiredFormattedCourse = requiredFormattedCourse;
	}

}
