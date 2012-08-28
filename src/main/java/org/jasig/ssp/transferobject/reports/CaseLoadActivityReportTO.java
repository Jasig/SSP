package org.jasig.ssp.transferobject.reports;

public class CaseLoadActivityReportTO {

	final String firstName;
	final String lastName;

	final Long journalEntriesCreatedCount;
	final Long studentsJournalEntriesCount;
	final Long actionPlanTasksCreatedCount;
	final Long studentsActionPlanTasksCount;
	final Long earlyAlertsCreatedCount;
	final Long studentsEarlyAlertsCount;
	final Long earlyAlertsRespondedCount;
	
	
	public CaseLoadActivityReportTO(String firstName, String lastName,
			Long journalEntriesCreatedCount, Long studentsJournalEntriesCount,
			Long actionPlanTasksCreatedCount,
			Long studentsActionPlanTasksCount, Long earlyAlertsCreatedCount,
			Long studentsEarlyAlertsCount, Long earlyAlertsRespondedCount) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.journalEntriesCreatedCount = journalEntriesCreatedCount;
		this.studentsJournalEntriesCount = studentsJournalEntriesCount;
		this.actionPlanTasksCreatedCount = actionPlanTasksCreatedCount;
		this.studentsActionPlanTasksCount = studentsActionPlanTasksCount;
		this.earlyAlertsCreatedCount = earlyAlertsCreatedCount;
		this.studentsEarlyAlertsCount = studentsEarlyAlertsCount;
		this.earlyAlertsRespondedCount = earlyAlertsRespondedCount;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Long getJournalEntriesCreatedCount() {
		return journalEntriesCreatedCount;
	}
	public Long getStudentsJournalEntriesCount() {
		return studentsJournalEntriesCount;
	}
	public Long getActionPlanTasksCreatedCount() {
		return actionPlanTasksCreatedCount;
	}
	public Long getStudentsActionPlanTasksCount() {
		return studentsActionPlanTasksCount;
	}
	public Long getEarlyAlertsCreatedCount() {
		return earlyAlertsCreatedCount;
	}
	public Long getStudentsEarlyAlertsCount() {
		return studentsEarlyAlertsCount;
	}
	public Long getEarlyAlertsRespondedCount() {
		return earlyAlertsRespondedCount;
	}
}
