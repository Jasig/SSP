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
package org.jasig.ssp.model.reports;

import java.util.HashMap;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * Caseload record
 * 
 * <p>
 * There isn't a 'CaseloadSumsRecord' table/model, but this psuedo-model is used by the
 * DAO layer to store aggregate data for use by the CaseloadReport controller and
 * service layers.
 */
public class CaseloadSumsRecord {

	@NotNull
	private UUID coachId; //meant to reference the coachId

	@NotNull
	private String schoolId;

	//@NotNull
	//private String department;
	
	@NotNull
	private Integer totalStudentsServed = new Integer(0);

	@NotNull
	private Integer targetAnnualCaseload = new Integer(0);

	@NotNull
	private Integer currentTotalCaseload = new Integer(0);
	
	@NotNull
	private HashMap<String,Integer> studentCountByStatus = new HashMap<String,Integer>();

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getTotalStudentsServed() {
		return totalStudentsServed;
	}

	public void setTotalStudentsServed(Integer totalStudentsServed) {
		this.totalStudentsServed = totalStudentsServed;
	}

	public Integer getTargetAnnualCaseload() {
		return targetAnnualCaseload;
	}

	public void setTargetAnnualCaseload(Integer targetAnnualCaseload) {
		this.targetAnnualCaseload = targetAnnualCaseload;
	}

	public Integer getCurrentTotalCaseload() {
		return currentTotalCaseload;
	}

	public void setCurrentTotalCaseload(Integer currentTotalCaseload) {
		this.currentTotalCaseload = currentTotalCaseload;
	}

	public HashMap<String, Integer> getStudentCountByStatus() {
		return studentCountByStatus;
	}

	public void setStudentCountByStatus(
			HashMap<String, Integer> studentCountByStatus) {
		this.studentCountByStatus = studentCountByStatus;
	}
}