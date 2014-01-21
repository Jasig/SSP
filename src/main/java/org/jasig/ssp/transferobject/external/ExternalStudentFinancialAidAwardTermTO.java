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
package org.jasig.ssp.transferobject.external;

import java.io.Serializable;

import org.jasig.ssp.model.external.ExternalStudentFinancialAidAwardTerm;

public class ExternalStudentFinancialAidAwardTermTO implements Serializable,
		ExternalDataTO<ExternalStudentFinancialAidAwardTerm> {

	/**
	 * @param schoolId
	 * @param accepted
	 * @param termCode
	 */
	public ExternalStudentFinancialAidAwardTermTO(String schoolId,
			String accepted,
			String termCode) {
		super();
		this.schoolId = schoolId;
		this.accepted = accepted;
		this.termCode = termCode;
	}
	
	public ExternalStudentFinancialAidAwardTermTO(ExternalStudentFinancialAidAwardTerm model) {
		super();
		this.schoolId = model.getSchoolId();
		this.accepted = model.getAccepted();
		this.termCode = model.getTermCode();
	}
	
	public ExternalStudentFinancialAidAwardTermTO()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8234383994143195272L;

	private String schoolId;
	
	private String accepted;
	
	private String termCode;
	
	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	/**
	 * @return the accepted
	 */
	public String getAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	@Override
	public void from(ExternalStudentFinancialAidAwardTerm model) {
		
		schoolId = model.getSchoolId();
		
		accepted = model.getAccepted();
		
		termCode = model.getTermCode();
	}

}
