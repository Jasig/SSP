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

import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;

public class ExternalStudentAcademicProgramTO implements Serializable,
	ExternalDataTO<ExternalStudentAcademicProgram> {

	
	public ExternalStudentAcademicProgramTO(){
		super();
	}
	
	/**
	 * @param degreeCode
	 * @param degreeName
	 * @param programCode
	 * @param programName
	 */
	public ExternalStudentAcademicProgramTO(String degreeCode,
			String degreeName, String programCode, String programName) {
		super();
		this.degreeCode = degreeCode;
		this.degreeName = degreeName;
		this.programCode = programCode;
		this.programName = programName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8614156729741653387L;
	private String degreeCode;
	private String degreeName;
	private String programCode;
	private String programName;
	
	
	@Override
	public void from(ExternalStudentAcademicProgram model) {
		degreeCode = model.getDegreeCode();
		degreeName = model.getDegreeCode();
		programCode = model.getProgramCode();
		programName = model.getProgramName();
	}

	/**
	 * @return the degreeCode
	 */
	public String getDegreeCode() {
		return degreeCode;
	}

	/**
	 * @param degreeCode the degreeCode to set
	 */
	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	/**
	 * @return the degreeName
	 */
	public String getDegreeName() {
		return degreeName;
	}

	/**
	 * @param degreeName the degreeName to set
	 */
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	/**
	 * @return the programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * @param programCode the programCode to set
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
