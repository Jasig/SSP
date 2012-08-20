package org.jasig.ssp.transferobject.reports;

public class CaseLoadReportTO {

	final String firstName;
	final String lastName;
	final String departmentName;
	final Long activeCount;
	final Long inActiveCount;
	final Long npCount;
	final Long transitionedCount;
	final Long noShowCount;
	
	
	
	
	
	public CaseLoadReportTO(String firstName, String lastName,
			String departmentName, Long activeCount, Long inActiveCount,
			Long npCount, Long transitionedCount, Long noShowCount) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentName = departmentName;
		this.activeCount = activeCount;
		this.inActiveCount = inActiveCount;
		this.npCount = npCount;
		this.transitionedCount = transitionedCount;
		this.noShowCount = noShowCount;
	}



	public String getFirstName() {
		return firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public String getDepartmentName() {
		return departmentName;
	}



	public Long getActiveCount() {
		return activeCount;
	}




	public Long getInActiveCount() {
		return inActiveCount;
	}






	public Long getNpCount() {
		return npCount;
	}




	public Long getTransitionedCount() {
		return transitionedCount;
	}





	public Long getNoShowCount() {
		return noShowCount;
	}
}
