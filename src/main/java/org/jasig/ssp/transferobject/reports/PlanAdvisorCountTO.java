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
package org.jasig.ssp.transferobject.reports;

public class PlanAdvisorCountTO {
	
	private String coachName;
	private Integer activePlanCount;
	private Integer inactivePlanCount;
	private Integer totalPlanCount;
	
	
	/**
	 * 
	 */
	public PlanAdvisorCountTO() {
		super();
	}
	/**
	 * @return the coachName
	 */
	public String getCoachName() {
		return coachName;
	}
	/**
	 * @param coachName the coachName to set
	 */
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	/**
	 * @return the activePlanCount
	 */
	public Integer getActivePlanCount() {
		return activePlanCount;
	}
	/**
	 * @param activePlanCount the activePlanCount to set
	 */
	public void setActivePlanCount(Integer activePlanCount) {
		this.activePlanCount = activePlanCount;
	}
	/**
	 * @return the inactivePlanCount
	 */
	public Integer getInactivePlanCount() {
		return inactivePlanCount;
	}
	/**
	 * @param inactivePlanCount the inactivePlanCount to set
	 */
	public void setInactivePlanCount(Integer inactivePlanCount) {
		this.inactivePlanCount = inactivePlanCount;
	}
	/**
	 * @return the totalPlanCount
	 */
	public Integer getTotalPlanCount() {
		return totalPlanCount;
	}
	/**
	 * @param totalPlanCount the totalPlanCount to set
	 */
	public void setTotalPlanCount(Integer totalPlanCount) {
		this.totalPlanCount = totalPlanCount;
	}

}
