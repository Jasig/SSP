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
package org.jasig.ssp.model.external;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "v_external_student_special_service_group")
public class ExternalStudentSpecialServiceGroup extends AbstractExternalData implements
		Serializable, ExternalData {

	private static final long serialVersionUID = -872213439743690003L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String code;
	
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
	 * @return the special service group code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the special service group code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}
}
