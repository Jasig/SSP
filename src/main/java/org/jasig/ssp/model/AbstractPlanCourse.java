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
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractPlanCourse extends AbstractAuditable implements Cloneable {

	private static final long serialVersionUID = 4387422660830382586L;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)
	private String termCode;
	
	@Column(length = 200 , nullable = false)
	@Size(max = 200)	
	private String courseCode;
	
	@Column(length = 200 , nullable = false)
	@Size(max = 200)
	private String formattedCourse;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)	
	private String courseTitle;
	
	@Column(length = 200, nullable = false)
	@Size(max = 200)	
	private String courseDescription;
	
	@Column(nullable = false)
	private Integer creditHours;
	
	@Column(nullable = false)
	private Boolean isDev;

	@Column(nullable = false)
	private Integer orderInTerm;
	
	@Override
	protected int hashPrime() {
		return 0;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public Integer getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Integer creditHours) {
		this.creditHours = creditHours;
	}

	public Boolean isDev() {
		return isDev;
	}

	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}

	public Integer getOrderInTerm() {
		return orderInTerm;
	}

	public void setOrderInTerm(Integer orderInTerm) {
		this.orderInTerm = orderInTerm;
	}

	
}
