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
package org.jasig.ssp.transferobject.external;

import java.math.BigDecimal;

import org.jasig.ssp.model.external.ExternalCourse;

public class ExternalCourseTO implements ExternalDataTO<ExternalCourse> {

	private String code;

	private String formattedCourse;

	private String subjectAbbreviation;
	
	private String title;
	
	private String description;
	
	private BigDecimal maxCreditHours;
	
	private BigDecimal minCreditHours;
	
	private Boolean isDev;
	
	private String tags;
	
	private String academicLink,masterSyllabusLink;

	private String departmentCode,divisionCode;
	
	private String number;
	

	public ExternalCourseTO() {
		super();
	}

	public ExternalCourseTO(final ExternalCourse model) {
		super();
		from(model);
	}

	@Override
	public final void from(final ExternalCourse model) {
		this.setCode(model.getCode());
		this.setDescription(model.getDescription());
		this.setFormattedCourse(model.getFormattedCourse());
		this.setMaxCreditHours(model.getMaxCreditHours());
		this.setMinCreditHours(model.getMinCreditHours());
		this.setSubjectAbbreviation(model.getSubjectAbbreviation());
		this.setTitle(model.getTitle());
		this.setIsDev(model.getIsDev());
		this.setAcademicLink(model.getAcademicLink());
		this.setMasterSyllabusLink(model.getMasterSyllabusLink());
		this.setDepartmentCode(model.getDepartmentCode());
		this.setDivisionCode(model.getDivisionCode());
		this.setNumber(model.getNumber());
		this.setTags(model.getPivotedTags());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}

	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getMaxCreditHours() {
		return maxCreditHours;
	}

	public void setMaxCreditHours(BigDecimal maxCreditHours) {
		this.maxCreditHours = maxCreditHours;
	}

	public BigDecimal getMinCreditHours() {
		return minCreditHours;
	}

	public void setMinCreditHours(BigDecimal minCreditHours) {
		this.minCreditHours = minCreditHours;
	}

	public Boolean getIsDev() {
		return isDev;
	}

	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAcademicLink() {
		return academicLink;
	}

	public void setAcademicLink(String academicLink) {
		this.academicLink = academicLink;
	}

	public String getMasterSyllabusLink() {
		return masterSyllabusLink;
	}

	public void setMasterSyllabusLink(String masterSyllabusLink) {
		this.masterSyllabusLink = masterSyllabusLink;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}