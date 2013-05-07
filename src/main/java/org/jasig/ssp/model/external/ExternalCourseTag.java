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

@Entity
@Immutable
@Table(name = "v_external_course_tag")
public class ExternalCourseTag extends AbstractExternalReferenceData implements ExternalData, Serializable {

	private static final long serialVersionUID = 1529384456357160956L;
	
	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String courseCode;

	
	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String tag;


	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
