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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapStatusReportSummary {

	private Calendar startTime;
	
	private Calendar endTime;
	
	private int studentsInScope;
	
	private boolean updateCurrentTerm;
	
	private List<MapStatusReportSummaryDetail> summaryDetails = new ArrayList<MapStatusReportSummaryDetail>();
	
	
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	public int getStudentsInScope() {
		return studentsInScope;
	}
	public void setStudentsInScope(int studentsInScope) {
		this.studentsInScope = studentsInScope;
	}
	public boolean isUpdateCurrentTerm() {
		return updateCurrentTerm;
	}
	public void setUpdateCurrentTerm(boolean updateCurrentTerm) {
		this.updateCurrentTerm = updateCurrentTerm;
	}
	public List<MapStatusReportSummaryDetail> getSummaryDetails() {
		return summaryDetails;
	}
	public void setSummaryDetails(List<MapStatusReportSummaryDetail> summaryDetails) {
		this.summaryDetails = summaryDetails;
	}



}
