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

import java.io.Serializable;

public class EarlyAlertTermCaseCountsTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String termCode;
	String termName;
	Long totalStudents;
	Long totalCases;
	Long totalRespondedTo;
	Long totalClosed;
	
	public EarlyAlertTermCaseCountsTO(String termCode, String termName,
			Long totalStudents, Long totalCases, Long totalRespondedTo,
			Long totalClosed) {
		super();
		this.termCode = termCode;
		this.termName = termName;
		this.totalStudents = totalStudents;
		this.totalCases = totalCases;
		this.totalRespondedTo = totalRespondedTo;
		this.totalClosed = totalClosed;
	}
	
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public Long getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(Long totalStudents) {
		this.totalStudents = totalStudents;
	}
	public Long getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(Long totalCases) {
		this.totalCases = totalCases;
	}
	public Long getTotalRespondedTo() {
		return totalRespondedTo;
	}
	public void setTotalRespondedTo(Long totalRespondedTo) {
		this.totalRespondedTo = totalRespondedTo;
	}
	public Long getTotalClosed() {
		return totalClosed;
	}
	public void setTotalClosed(Long totalClosed) {
		this.totalClosed = totalClosed;
	}


}
