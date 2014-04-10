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
		reports.add(new Pair<String,String>("earlyAlertStudent","Early Alert Student Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentReferral","Early Alert Student Referral Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentProgress","Early Alert Student Progress Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentOutreach","Early Alert Student Outreach Report"));
		reports.add(new Pair<String,String>("earlyAlertStudentOutcome","Early Alert Student Outcome Report"));
		reports.add(new Pair<String,String>(null,"- Journal/Task Reports -"));
		reports.add(new Pair<String,String>("journalSessionDetail","Journal Step Detail Report"));
		reports.add(new Pair<String,String>(null, "- MAP Reports -"));
		reports.add(new Pair<String,String>("numberCoursesInPlan","Number of Courses in Plans"));
		reports.add(new Pair<String,String>("numberPlansByAdvisor","Number of Plans by Advisor"));
		reports.add(new Pair<String,String>("numberStudentsByStatus","Number of Students by Status"));
	}
	
	public List<Pair<String, String>> getReports() {
		return reports;
	}

	public void setReports(List<Pair<String, String>> reports) {
		this.reports = reports;
	}

}
