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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.AbstractMapElectiveCourse;

import java.math.BigDecimal;

public class AbstractMapElectiveCourseTO<T extends AbstractMapElectiveCourse> extends
			AbstractAuditableTO<T> implements TransferObject<T> {
	private String courseCode;

	private String formattedCourse;

	private String courseTitle;

	private String courseDescription;

	private BigDecimal creditHours;

	/**
	 * Empty constructor.
	 */
	public AbstractMapElectiveCourseTO() {
		super();
	}

	public void from(T model) {
		super.from(model);
		this.setCourseCode(model.getCourseCode());
		this.setCourseDescription(model.getCourseDescription());
		this.setCourseTitle(model.getCourseTitle());
		this.setFormattedCourse(model.getFormattedCourse());
		this.setCreditHours(model.getCreditHours());
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

	public BigDecimal getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
	}
}
