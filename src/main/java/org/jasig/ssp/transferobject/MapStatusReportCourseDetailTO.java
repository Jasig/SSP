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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.MapStatusReportCourseDetails;

/**
 */
public class MapStatusReportCourseDetailTO extends
AbstractAuditableTO<MapStatusReportCourseDetails> implements TransferObject<MapStatusReportCourseDetails> {

	private String termCode;
	
	private String formattedCourse;
	
	private String courseCode;
	
	private String anomalyNote;
	
	private String anomalyCode;
	
	/**
	 * Empty constructor.
	 */
	public MapStatusReportCourseDetailTO() {
		super();
	}
	/**
	 * Empty constructor.
	 */
	public MapStatusReportCourseDetailTO(MapStatusReportCourseDetails model) {
		super();
		from(model);
	}
	

	public void from(MapStatusReportCourseDetails model) {
		super.from(model);
		this.setTermCode(model.getTermCode());
		this.setAnomalyCode(model.getAnomalyCode().getDisplayText());
		this.setCourseCode(model.getCourseCode());
		this.setFormattedCourse(model.getFormattedCourse());
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
	public String getAnomalyCode() {
		return anomalyCode;
	}
	public void setAnomalyCode(String anomalyCode) {
		this.anomalyCode = anomalyCode;
	}

}