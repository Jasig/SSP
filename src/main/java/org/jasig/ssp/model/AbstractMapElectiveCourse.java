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
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractMapElectiveCourse extends AbstractAuditable implements Cloneable {

	@Column(length = 50 , nullable = false)
	@Size(max = 50)
	private String courseCode;

	@Column(length = 35 , nullable = false)
	@Size(max = 35)
	private String formattedCourse;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)
	private String courseTitle;

	@Column(length = 2000, nullable = false)
	@Size(max = 2000)
	private String courseDescription;

	@Column(nullable = false)
	private BigDecimal creditHours;

	@Transient
	private Boolean isDirty = false;	

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T extends AbstractMapElectiveCourse> void cloneCommonFields(T clone) {
		clone.setCourseCode(this.getCourseCode());
		clone.setFormattedCourse(this.getFormattedCourse());
		clone.setCourseDescription(this.getCourseDescription());
		clone.setCourseTitle(this.getCourseTitle());
	}

	public Boolean getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public BigDecimal getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
	}
}
