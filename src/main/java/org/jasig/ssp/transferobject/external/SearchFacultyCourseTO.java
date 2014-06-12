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

import org.apache.commons.lang.StringUtils;


public class SearchFacultyCourseTO {
	private final String facultySchoolId;
	private final String termCode;
	private final String sectionCode;
	private final String formattedCourse;
	
	public SearchFacultyCourseTO(String facultyId, String termCode,
			String sectionCode, String formattedCourse) {
		super();
		this.facultySchoolId = facultyId;
		this.termCode = termCode;
		this.sectionCode = sectionCode;
		this.formattedCourse = formattedCourse;
	}

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}
	
	public Boolean hasFacultySchoolId(){
		return StringUtils.isNotBlank(facultySchoolId);
	}
	
	public Boolean hasTermCode(){
		return StringUtils.isNotBlank(termCode);
	}

	
	public Boolean hasSectionCode(){
		return StringUtils.isNotBlank(sectionCode);
	}

	
	public Boolean hasFormattedCourse(){
		return StringUtils.isNotBlank(formattedCourse);
	}
}
