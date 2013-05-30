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

import java.util.Comparator;


public class PlanAdvisorCountTO {
	
	
	public static class PlanAdvisorCoachNameComparator implements Comparator<PlanAdvisorCountTO> {
		 @Override
		    public int compare(PlanAdvisorCountTO o1, PlanAdvisorCountTO o2) {
		        return o1.getCoachName().trim().compareTo(o2.getCoachName().trim());
		    }

	}

	public static final PlanAdvisorCoachNameComparator COACH_NAME_COMPARATOR =
			new PlanAdvisorCoachNameComparator();
	
	private String coachName;
	private Long activePlanCount  = 0L;
	private Long inactivePlanCount = 0L;
	private Long totalPlanCount = 0L;
	
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
	public Long getActivePlanCount() {
		return activePlanCount;
	}
	/**
	 * @param activePlanCount the activePlanCount to set
	 */
	public void setActivePlanCount(Long activePlanCount) {
		this.activePlanCount = activePlanCount;
		totalPlanCount = activePlanCount + inactivePlanCount;
	}
	/**
	 * @return the inactivePlanCount
	 */
	public Long getInactivePlanCount() {
		
		return inactivePlanCount;
	}
	/**
	 * @param inactivePlanCount the inactivePlanCount to set
	 */
	public void setInactivePlanCount(Long inactivePlanCount) {
		totalPlanCount = activePlanCount + inactivePlanCount;
		this.inactivePlanCount = inactivePlanCount;
	}
	/**
	 * @return the totalPlanCount
	 */
	public Long getTotalPlanCount() {
		return totalPlanCount;
	}
	
	public void setTotalPlanCount(Long totalPlanCount) {
		 this.totalPlanCount = totalPlanCount;
	}

}
