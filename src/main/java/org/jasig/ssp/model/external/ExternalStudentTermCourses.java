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
import java.util.List;

import com.google.common.collect.Lists;

public class ExternalStudentTermCourses extends AbstractExternalData implements
ExternalData, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -180393846186163477L;
	private String code;
	private List<ExternalStudentTranscriptCourse> courses;
	
	public ExternalStudentTermCourses(ExternalStudentTranscriptCourse course){
		super();
		courses = Lists.newArrayList(course);
		code = course.getTermCode();
	}
	
	/**
	 * @return the term
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param term the term to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the courses
	 */
	public List<ExternalStudentTranscriptCourse> getCourses() {
		return courses;
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<ExternalStudentTranscriptCourse> courses) {
		this.courses = courses;
	}
	
	public void addCourse(ExternalStudentTranscriptCourse course){
		courses.add(course);
	}
}
