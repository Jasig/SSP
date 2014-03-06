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
package org.jasig.ssp.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MapStatusReportCourseDetails extends AbstractAuditable implements Auditable  {

	
	public MapStatusReportCourseDetails(MapStatusReport report,
			String termCode, String formattedCourse, String courseCode,
			String anomalyNote, AnomalyCode anomalyCode) {
		super();
		this.report = report;
		this.termCode = termCode;
		this.formattedCourse = formattedCourse;
		this.courseCode = courseCode;
		this.anomalyNote = anomalyNote;
		this.setAnomalyCode(anomalyCode);
	}

	private static final long serialVersionUID = -5993530370454729414L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id", updatable = false, nullable = false)
	private MapStatusReport report;
	
	@Column(length = 25)
	@Size(max = 25)
	private String termCode;
	
	@Column(length = 35)
	@Size(max = 35)
	private String formattedCourse;
	
	@Column(length = 50)
	@Size(max = 50)
	private String courseCode;
	
	@Column(length = 500)
	@Size(max = 500)
	private String anomalyNote;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AnomalyCode anomalyCode;
	
	public MapStatusReportCourseDetails(UUID id) {
		super();
		setId(id);
	}

	public MapStatusReportCourseDetails() {
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

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getAnomalyNote() {
		return anomalyNote;
	}

	public void setAnomalyNote(String anomalyNote) {
		this.anomalyNote = anomalyNote;
	}

	public AnomalyCode getAnomalyCode() {
		return anomalyCode;
	}

	public void setAnomalyCode(AnomalyCode anomalyCode) {
		this.anomalyCode = anomalyCode;
	}


}
