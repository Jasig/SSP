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
 * RegistrationStatusByTerm external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_registration_status_by_term")
public class RegistrationStatusByTerm extends AbstractExternalData
		implements Serializable, ExternalData {

	private static final long serialVersionUID = -2034426964159477894L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;

	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;

	@NotNull
	@Column(nullable = false, length = 25)
	private int registeredCourseCount;

	@Column(nullable = true, length = 1)
	private String tuitionPaid;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	public int getRegisteredCourseCount() {
		return registeredCourseCount;
	}

	public void setRegisteredCourseCount(final int registeredCourseCount) {
		this.registeredCourseCount = registeredCourseCount;
	}

	/**
	 * @return the tuitionPaid
	 */
	public String getTuitionPaid() {
		return tuitionPaid;
	}

	/**
	 * @param tuitionPaid the tuitionPaid to set
	 */
	public void setTuitionPaid(final String tuitionPaid) {
		this.tuitionPaid = tuitionPaid;
	}

}
