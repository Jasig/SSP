/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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
	
	public ExternalStudentAcademicProgramTO(ExternalStudentAcademicProgram model){
		super();
		from(model);
	}
	
	
	/**
	 * @param degreeCode the degree code
	 * @param degreeName the degree name
	 * @param programCode the program code
	 * @param programName the program name
	 * @param intendedProgramAtAdmit  the intended program at admit
	 */
	public ExternalStudentAcademicProgramTO(String degreeCode,
			String degreeName, String programCode, String programName, String intendedProgramAtAdmit) {
		super();
		this.degreeCode = degreeCode;
		this.degreeName = degreeName;
		this.programCode = programCode;
		this.programName = programName;
		this.intendedProgramAtAdmit = intendedProgramAtAdmit;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8614156729741653387L;
	private String degreeCode;
	private String degreeName;
	private String programCode;
	private String programName;
	private String intendedProgramAtAdmit;
	
	
	@Override
	public void from(ExternalStudentAcademicProgram model) {
		degreeCode = model.getDegreeCode();
		degreeName = model.getDegreeCode();
		programCode = model.getProgramCode();
		programName = model.getProgramName();
		intendedProgramAtAdmit = model.getIntendedProgramAtAdmit();
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

	/**
	 * @return the intendedProgramAtAdmit
	 */
	public String getIntendedProgramAtAdmit() {
		return intendedProgramAtAdmit;
	}

	/**
	 * @param intendedProgramAtAdmit the intendedProgramAtAdmit to set
	 */
	public void setIntendedProgramAtAdmit(String intendedProgramAtAdmit) {
		this.intendedProgramAtAdmit = intendedProgramAtAdmit;
	}

}
