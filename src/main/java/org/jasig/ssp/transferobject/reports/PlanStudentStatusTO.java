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

import java.util.Comparator;

import org.jasig.ssp.model.ObjectStatus;

public class PlanStudentStatusTO {
	
	public static class PlanStudentStatusStudentIdComparator implements Comparator<PlanStudentStatusTO> {
		 @Override
		    public int compare(PlanStudentStatusTO o1, PlanStudentStatusTO o2) {
		        return o1.getStudentId().trim().compareTo(o2.getStudentId().trim());
		    }

	}

	public static final PlanStudentStatusStudentIdComparator STUDENT_ID_COMPARATOR =
			new PlanStudentStatusStudentIdComparator();

	private String studentId;
	private String courseTitle;
	private String formattedCourse;
	private String planStatus;
	private ObjectStatus planObjectStatus;
	private String[] statusDetails;
	
	/**
	 * 
	 */
	public PlanStudentStatusTO() {
		super();
	}
	
	
	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the planStatus
	 */
	public String getPlanStatus() {
		return planStatus;
	}
	/**
	 * @param planStatus the planStatus to set
	 */
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	/**
	 * @return the statusDetails
	 */
	public String[] getStatusDetails() {
		return statusDetails;
	}
	/**
	 * @param statusDetails the statusDetails to set
	 */
	public void setStatusDetails(String[] statusDetails) {
		this.statusDetails = statusDetails;
	}


	/**
	 * @return the planObjectStatus
	 */
	public ObjectStatus getPlanObjectStatus() {
		return planObjectStatus;
	}


	/**
	 * @param planObjectStatus the planObjectStatus to set
	 */
	public void setPlanObjectStatus(ObjectStatus planObjectStatus) {
		this.planObjectStatus = planObjectStatus;
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
	
}
