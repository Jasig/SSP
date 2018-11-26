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

import java.util.Comparator;

public class PlanStudentCoursesCountTO {

	public static class PlanStudentStatusStudentIdComparator implements Comparator<PlanStudentCoursesCountTO> {
		 @Override
		    public int compare(PlanStudentCoursesCountTO o1, PlanStudentCoursesCountTO o2) {
		        return o1.getSchoolId().trim().compareTo(o2.getSchoolId().trim());
		    }
	}

	public static final PlanStudentStatusStudentIdComparator STUDENT_ID_COMPARATOR =
			new PlanStudentStatusStudentIdComparator();

	private String schoolId;
	private String username;
	private String firstName;
	private String lastName;
	private String primaryEmailAddress;
	private Long plannedCourses;

	/**
	 *
	 */
	public PlanStudentCoursesCountTO() {
		super();
	}


	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public Long getPlannedCourses() {
		return plannedCourses;
	}

	public void setPlannedCourses(Long plannedCourses) {
		this.plannedCourses = plannedCourses;
	}
}
