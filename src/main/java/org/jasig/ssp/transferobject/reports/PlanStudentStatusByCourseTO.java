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
package org.jasig.ssp.transferobject.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO.PlanStudentStatusStudentIdComparator;

public class PlanStudentStatusByCourseTO {
	
	public static class PlanStudentStatusFormattedCourseComparator implements Comparator<PlanStudentStatusByCourseTO> {
		 @Override
		    public int compare(PlanStudentStatusByCourseTO o1, PlanStudentStatusByCourseTO o2) {
		        return o1.getFormattedCourse().trim().compareTo(o2.getFormattedCourse().trim());
		    }

	}

	public static final PlanStudentStatusFormattedCourseComparator FORMATTED_COURSE_COMPARATOR =
			new PlanStudentStatusFormattedCourseComparator();
	
	private String formattedCourse;
	private String courseTitle;
	private List<PlanStudentStatusTO> studentStatusByCourse = new ArrayList<PlanStudentStatusTO>();
	
	/**
	 * @param formattedCourse the formatted course
	 * @param courseTitle the course title
	 */
	public PlanStudentStatusByCourseTO(String formattedCourse,
			String courseTitle) {
		super();
		this.formattedCourse = formattedCourse;
		this.courseTitle = courseTitle;
	}
	/**
	 * @return the formattedCourse
	 */
	public String getFormattedCourse() {
		return formattedCourse;
	}
	/**
	 * @param formattedCourse the formattedCourse to set
	 */
	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}
	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}
	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	/**
	 * @return the studentStatusByCourse
	 */
	public List<PlanStudentStatusTO> getStudentStatusByCourse() {
		Collections.sort(studentStatusByCourse, PlanStudentStatusTO.STUDENT_ID_COMPARATOR);
		return studentStatusByCourse;
	}
	/**
	 * @param studentStatusByCourse the studentStatusByCourse to set
	 */
	public void setStudentStatusByCourse(
			List<PlanStudentStatusTO> studentStatusByCourse) {
		this.studentStatusByCourse = studentStatusByCourse;
	}
	
	public void addStudentStatus(PlanStudentStatusTO studentStatus){
		this.studentStatusByCourse.add(studentStatus);
	}
	
	public String getCourseName(){
		return this.formattedCourse.trim() + " - " + this.courseTitle.trim();
	}

}
