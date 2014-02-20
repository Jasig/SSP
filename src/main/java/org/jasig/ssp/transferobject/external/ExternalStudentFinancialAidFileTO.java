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
import org.jasig.ssp.model.external.ExternalStudentFinancialAidFile;
import org.jasig.ssp.model.external.FinancialFileStatus;

public class ExternalStudentFinancialAidFileTO implements Serializable,
		ExternalDataTO<ExternalStudentFinancialAidFile> {

	/**
	 * @param schoolId
	 * @param financialFileCode
	 * @param fileStatus
	 */
	public ExternalStudentFinancialAidFileTO(String schoolId,
			String financialFileCode,
			FinancialFileStatus fileStatus) {
		super();
		this.schoolId = schoolId;
		this.financialFileCode = financialFileCode;
		this.fileStatus = fileStatus;
	}
	
	public ExternalStudentFinancialAidFileTO(ExternalStudentFinancialAidFile model) {
		super();
		this.schoolId = model.getSchoolId();
		this.financialFileCode = model.getFinancialFileCode();
		this.fileStatus = model.getFileStatus();
	}
	
	public ExternalStudentFinancialAidFileTO()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8234383994143195272L;

	private String schoolId;
	
	private String financialFileCode;
	
	private FinancialFileStatus fileStatus;

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

	
	public String getFinancialFileCode() {
		return financialFileCode;
	}

	public void setFinancialFileCode(String financialFileCode) {
		this.financialFileCode = financialFileCode;
	}

	public FinancialFileStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FinancialFileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Override
	public void from(ExternalStudentFinancialAidFile model) {
		schoolId = model.getSchoolId();
		fileStatus = model.getFileStatus();
		financialFileCode = model.getFinancialFileCode();
	}

}
