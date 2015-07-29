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
package org.jasig.ssp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MapStatusReportOverrideDetails extends AbstractAuditable implements Auditable  {


	private static final long serialVersionUID = -5993530678454729414L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id", updatable = false, nullable = false)
	private MapStatusReport report;

	@Column(length = 25)
	@Size(max = 25)
	private String termCode;

	@Column(length = 35)
	@Size(max = 35)
	private String targetFormattedCourse;

	@Column(length = 50)
	@Size(max = 50)
	private String nonCourseCode;

	@Column(length = 100)
	@Size(max = 100)
	private String description;

    @Column(length = 500)
    @Size(max = 500)
    private String overrideNote;


	public MapStatusReportOverrideDetails (UUID id) {
		super();
		setId(id);
	}

	public MapStatusReportOverrideDetails () {
		super();
	}

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public MapStatusReport getReport() {
		return report;
	}

	public void setReport(MapStatusReport report) {
		this.report = report;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getTargetFormattedCourse() {
		return targetFormattedCourse;
	}

	public void setTargetFormattedCourse(String targetFormattedCourse) {
		this.targetFormattedCourse = targetFormattedCourse;
	}

	public String getNonCourseCode() {
		return nonCourseCode;
	}

	public void setNonCourseCode(String nonCourseCode) {
		this.nonCourseCode = nonCourseCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOverrideNote() {
		return overrideNote;
	}

	public void setOverrideNote(String overrideNote) {
		this.overrideNote = overrideNote;
	}
}
