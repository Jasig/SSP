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

import org.jasig.ssp.model.external.ExternalCourse;

public class ExternalCourseTO implements ExternalDataTO<ExternalCourse> {

	private String code;

	private String formattedCourse;

	private String subjectAbbreviation;
	
	private String title;
	
	private String description;
	
	private Integer maxCreditHours;
	
	private Integer minCreditHours;
	
	private Boolean isDev;

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

	public Integer getMaxCreditHours() {
		return maxCreditHours;
	}

	public void setMaxCreditHours(Integer maxCreditHours) {
		this.maxCreditHours = maxCreditHours;
	}

	public Integer getMinCreditHours() {
		return minCreditHours;
	}

	public void setMinCreditHours(Integer minCreditHours) {
		this.minCreditHours = minCreditHours;
	}

	public Boolean getIsDev() {
		return isDev;
	}

	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}


}