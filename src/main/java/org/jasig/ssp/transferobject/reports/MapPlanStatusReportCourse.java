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
package org.jasig.ssp.transferobject.reports;

import java.math.BigDecimal;
import java.util.UUID;

public class MapPlanStatusReportCourse {

	public MapPlanStatusReportCourse(String termCode, String formattedCourse,
			String courseCode, String courseTitle, BigDecimal creditHours) {
		super();
		this.termCode = termCode;
		this.formattedCourse = formattedCourse;
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.creditHours = creditHours;
	}

	private String termCode;
	private String formattedCourse;
	private String courseCode;
	private String courseTitle;
	private BigDecimal creditHours;
	
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
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseTitle() {
		return courseTitle;
	}
	
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public BigDecimal getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
	}
}
