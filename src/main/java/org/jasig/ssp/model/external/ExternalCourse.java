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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_course")
public class ExternalCourse extends AbstractExternalReferenceData implements ExternalData, Serializable {

	private static final long serialVersionUID = 1529384456357160956L;
	public static final String BOOLEAN_YES = "Y";
	public static final String BOOLEAN_NO = "N";
	

	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String formattedCourse;

	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String subjectAbbreviation;

	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String title;
	
	@Column(nullable = false, length = 2500)
	@NotNull
	@NotEmpty
	@Size(max = 2500)
	private String description;

	@Column(nullable = false)
	private Integer maxCreditHours,minCreditHours;

	@Column(nullable = false, length = 1)
	@Size(max = 1)
	private String isDev;

	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}

	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
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
		String isDevTrimmed = StringUtils.trimToNull(isDev);
		if ( isDevTrimmed == null ) {
			// non-nullable field so no point in returning null here
			return false;
		}
		return BOOLEAN_YES.equalsIgnoreCase(isDevTrimmed);
	}

	public void setIsDev(Boolean isDev) {
		if ( isDev == null ) {
			this.isDev = BOOLEAN_NO;
		}
		this.isDev = isDev ? BOOLEAN_YES : BOOLEAN_NO;
	}
}
