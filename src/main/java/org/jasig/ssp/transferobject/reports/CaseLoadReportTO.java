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
