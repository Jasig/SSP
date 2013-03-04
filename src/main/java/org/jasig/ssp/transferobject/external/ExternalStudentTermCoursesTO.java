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

import java.io.Serializable;
import java.util.List;

import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTermCourses;

public class ExternalStudentTermCoursesTO implements ExternalDataTO<ExternalStudentTermCourses>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1802980832496414863L;
	
	String code;
	List<ExternalStudentTranscriptCourseTO> courses;
	
	public ExternalStudentTermCoursesTO(ExternalStudentTermCourses model){
		from(model);
	}
	
	/**
	 * 
	 */
	@Override
	public void from(ExternalStudentTermCourses model) {
		code = model.getCode();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the courses
	 */
	public List<ExternalStudentTranscriptCourseTO> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<ExternalStudentTranscriptCourseTO> courses) {
		this.courses = courses;
	}

}
