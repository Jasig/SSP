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
package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * FacultyCourse external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_student_academic_program")
public class ExternalStudentAcademicProgram  extends AbstractExternalData implements
Serializable, ExternalData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7437187541913664765L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String degreeCode;
	
	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String degreeName;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String programCode;
	
	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String programName;

	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
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
	public void setDegreeCode(final String degreeCode) {
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
	public void setDegreeName(final String degreeName) {
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
	public void setProgramCode(final String programCode) {
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
	public void setProgramName(final String programName) {
		this.programName = programName;
	}

}
