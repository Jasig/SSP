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

import java.math.BigDecimal;

import org.jasig.ssp.model.MapStatusReport;

/**
 */
public class MapStatusReportTO extends
AbstractAuditableTO<MapStatusReport> implements TransferObject<MapStatusReport> {

	private String personId;
	
	private String planStatus;
	
	private String planNote;
	
	private String planRatio;
	
	private String planRatioFraction;
	
	private int totalPlanCourses;
	
	private int planRatioDemerits;
	
	/**
	 * Empty constructor.
	 */
	public MapStatusReportTO() {
		super();
	}
	/**
	 * Empty constructor.
	 */
	public MapStatusReportTO(MapStatusReport model) {
		super();
		from(model);
	}
	

	public void from(MapStatusReport model) {
		super.from(model);
		this.setPersonId(model.getPerson().getId().toString());
		this.setPlanNote(model.getPlanNote());
		this.setPlanRatio(model.getPlanRatio().multiply(new BigDecimal(100)).intValue()+"%");
		this.setPlanRatioDemerits(model.getPlanRatioDemerits());
		this.setTotalPlanCourses(model.getTotalPlanCourses());
		this.setPlanRatioFraction((model.getTotalPlanCourses()-model.getPlanRatioDemerits())+"/"+model.getTotalPlanCourses());
		this.setPlanStatus(model.getPlanStatus().getDisplayName());
	}

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public String getPlanNote() {
		return planNote;
	}
	public void setPlanNote(String planNote) {
		this.planNote = planNote;
	}
	public String getPlanRatio() {
		return planRatio;
	}
	public void setPlanRatio(String planRatio) {
		this.planRatio = planRatio;
	}
	public int getTotalPlanCourses() {
		return totalPlanCourses;
	}
	public void setTotalPlanCourses(int totalPlanCourses) {
		this.totalPlanCourses = totalPlanCourses;
	}
	public int getPlanRatioDemerits() {
		return planRatioDemerits;
	}
	public void setPlanRatioDemerits(int planRatioDemerits) {
		this.planRatioDemerits = planRatioDemerits;
	}
	public String getPlanRatioFraction() {
		return planRatioFraction;
	}
	public void setPlanRatioFraction(String planRatioFraction) {
		this.planRatioFraction = planRatioFraction;
	}

}