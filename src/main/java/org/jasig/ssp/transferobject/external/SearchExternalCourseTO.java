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
package org.jasig.ssp.transferobject.external;

import org.apache.commons.lang.StringUtils;

public class SearchExternalCourseTO {
	
	private String programCode;
	private String termCode;
	private String subjectAbbreviation;
	private String courseNumber;
	private String tag;
	/**
	 * @param programCode the program code
	 * @param termCode the term code
	 * @param subjectAbbreviation the subject abbreviation
	 * @param courseNumber the course number
	 * @param tag the tag
	 */
	public SearchExternalCourseTO(String programCode, String termCode,
			String subjectAbbreviation, String courseNumber, String tag) {
		super();
		this.programCode = programCode;
		this.termCode = termCode;
		this.subjectAbbreviation = subjectAbbreviation;
		this.courseNumber = courseNumber;
		this.tag = tag;
	}
	/**
	 * @return the programCode
	 */
	public String getProgramCode() {
		return programCode;
	}
	/**
	 * @param programCode the programCode to set
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}
	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	/**
	 * @return the subjectAbbreviation
	 */
	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}
	/**
	 * @param subjectAbbreviation the subjectAbbreviation to set
	 */
	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}
	/**
	 * @return the courseNumber
	 */
	public String getCourseNumber() {
		return courseNumber;
	}
	/**
	 * @param courseNumber the courseNumber to set
	 */
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean isUnbounded() {
	return StringUtils.isEmpty(this.tag) && StringUtils.isEmpty(this.courseNumber)  
		&& StringUtils.isEmpty(this.programCode) && StringUtils.isEmpty(this.subjectAbbreviation)  
		&& StringUtils.isEmpty(this.termCode);	
	}
}
