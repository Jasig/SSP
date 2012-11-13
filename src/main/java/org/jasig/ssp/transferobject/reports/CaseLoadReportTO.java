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

	private String firstName;
	private String lastName;
	private String departmentName;
	private long activeCount;
	private long inActiveCount;
	private long npCount;
	private long transitionedCount;
	private long noShowCount;

	public CaseLoadReportTO(String firstName, String lastName,
							String departmentName) {
		this(firstName, lastName, departmentName, 0, 0, 0, 0, 0);
	}

	public CaseLoadReportTO(String firstName, String lastName,
			String departmentName, long activeCount, long inActiveCount,
			long npCount, long transitionedCount, long noShowCount) {
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(long activeCount) {
		this.activeCount = activeCount;
	}

	public void addToActiveCount(long add) {
		this.activeCount += add;
	}

	public long getInActiveCount() {
		return inActiveCount;
	}

	public void setInActiveCount(long inActiveCount) {
		this.inActiveCount = inActiveCount;
	}

	public void addToInActiveCount(long add) {
		this.inActiveCount += add;
	}

	public long getNpCount() {
		return npCount;
	}

	public void setNpCount(long npCount) {
		this.npCount = npCount;
	}

	public void addToNpCount(long add) {
		this.npCount += add;
	}

	public long getTransitionedCount() {
		return transitionedCount;
	}

	public void setTransitionedCount(long transitionedCount) {
		this.transitionedCount = transitionedCount;
	}

	public void addToTransitionedCount(long add) {
		this.transitionedCount += add;
	}

	public long getNoShowCount() {
		return noShowCount;
	}

	public void setNoShowCount(long noShowCount) {
		this.noShowCount = noShowCount;
	}

	public void addToNoShowCount(long add) {
		this.noShowCount += add;
	}
}
