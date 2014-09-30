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
import java.util.List;

import org.jasig.ssp.util.collections.Pair;

public class ReportListTO {

	List<Pair<String,String>> reports = new ArrayList<Pair<String,String>>();

	public ReportListTO(Boolean accomodationPermission) {
		reports.add(new Pair<String,String>("generalStudentForm","General Student Report"));
		reports.add(new Pair<String,String>("caseLoadForm","Current Caseload Statuses Report"));		
		reports.add(new Pair<String,String>("caseloadActivityReport","Caseload Activity Report"));			
		reports.add(new Pair<String,String>("confidentialityAgreementForm","Confidentiality Agreement"));		
		reports.add(new Pair<String,String>("counselingRefGuideForm","Counseling Reference Guide"));		
		reports.add(new Pair<String,String>("counselorCaseManagementReport","Counselor Case Management Report"));
		reports.add(new Pair<String,String>("specialServicesForm","Special Services Report"));
		if(accomodationPermission)
			reports.add(new Pair<String,String>("disabilityServices","Disability Services Report"));
		reports.add(new Pair<String,String>(null,"- Early Alert Reports -"));
		reports.add(new Pair<String,String>("earlyAlertCaseCounts","Early Alert Case Counts Report"));
        reports.add(new Pair<String,String>("earlyAlertCourseCounts","Early Alert Course Counts Report"));
        reports.add(new Pair<String,String>("earlyAlertReasonCounts","Early Alert Reason Counts Report"));
		reports.add(new Pair<String,String>("earlyAlertStudent","Early Alert Student Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentReferral","Early Alert Student Referral Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentProgress","Early Alert Student Progress Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentOutreach","Early Alert Student Outreach Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentOutcome","Early Alert Student Outcome Report"));
		reports.add(new Pair<String,String>(null,"- Journal/Task Reports -"));
		reports.add(new Pair<String,String>("journalSessionDetail","Journal Step Detail Report"));
		reports.add(new Pair<String,String>(null, "- MAP Reports -"));
		reports.add(new Pair<String,String>("numberCoursesInPlan","Number of Plans by Course"));
		reports.add(new Pair<String,String>("numberPlansByOwner","Number of Plans by Owner"));
		reports.add(new Pair<String,String>("numberStudentsByStatus","Number of Students by Status"));
	}
	
	public List<Pair<String, String>> getReports() {
		return reports;
	}

	public void setReports(List<Pair<String, String>> reports) {
		this.reports = reports;
	}

}
