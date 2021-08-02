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
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.MapStatusReportOverrideDetails;
import org.jasig.ssp.model.MapStatusReportSubstitutionDetails;
import org.jasig.ssp.model.SubstitutionCode;

/**
 */
public class MapStatusReportSubstitutionDetailTO extends
AbstractAuditableTO<MapStatusReportSubstitutionDetails> implements TransferObject<MapStatusReportSubstitutionDetails> {

	private String termCode;
	
	private String formattedCourse;
	
	private String courseCode;
	
	private String substitutedTermCode;
	
	private String substitutedFormattedCourse;
	
	private String substitutedCourseCode;
	
	private String substitutionNote;
	
	private String substitutionCode;
	
	/**
	 * Empty constructor.
	 */
	public MapStatusReportSubstitutionDetailTO() {
		super();
	}
	/**
	 * Empty constructor.
	 *
	 * @param model The MapStatusReportSubstitutionDetails model
	 */
	public MapStatusReportSubstitutionDetailTO(MapStatusReportSubstitutionDetails model) {
		super();
		from(model);
		this.setTermCode(model.getTermCode());
		this.setCourseCode(model.getCourseCode());
		this.setFormattedCourse(model.getFormattedCourse());
		this.setSubstitutedCourseCode(model.getSubstitutedCourseCode());
		this.setSubstitutedFormattedCourse(model.getSubstitutedFormattedCourse());
		this.setSubstitutedTermCode(model.getSubstitutedTermCode());
		this.setSubstitutionCode(model.getSubstitutionCode().getDisplayText());
		this.setSubstitutionNote(model.getSubstitutionNote());
	}

	public MapStatusReportSubstitutionDetailTO(MapStatusReportOverrideDetails model) {
		super();
		this.setTermCode(model.getTermCode());
		this.setCourseCode(model.getTargetFormattedCourse());
		this.setFormattedCourse(model.getTargetFormattedCourse());
		this.setSubstitutedCourseCode(model.getNonCourseCode());
		this.setSubstitutedFormattedCourse(model.getNonCourseCode());
		this.setSubstitutedTermCode(model.getTermCode());
		this.setSubstitutionCode(SubstitutionCode.OVERRIDE_COURSE.getDisplayText());
		this.setSubstitutionNote(model.getOverrideNote());
	}


	public void from(MapStatusReportSubstitutionDetails model) {
		super.from(model);
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
	public String getSubstitutedTermCode() {
		return substitutedTermCode;
	}
	public void setSubstitutedTermCode(String substitutedTermCode) {
		this.substitutedTermCode = substitutedTermCode;
	}
	public String getSubstitutedFormattedCourse() {
		return substitutedFormattedCourse;
	}
	public void setSubstitutedFormattedCourse(String substitutedFormattedCourse) {
		this.substitutedFormattedCourse = substitutedFormattedCourse;
	}
	public String getSubstitutedCourseCode() {
		return substitutedCourseCode;
	}
	public void setSubstitutedCourseCode(String substitutedCourseCode) {
		this.substitutedCourseCode = substitutedCourseCode;
	}
	public String getSubstitutionNote() {
		return substitutionNote;
	}
	public void setSubstitutionNote(String substitutionNote) {
		this.substitutionNote = substitutionNote;
	}
	public String getSubstitutionCode() {
		return substitutionCode;
	}
	public void setSubstitutionCode(String substitutionCode) {
		this.substitutionCode = substitutionCode;
	}

}