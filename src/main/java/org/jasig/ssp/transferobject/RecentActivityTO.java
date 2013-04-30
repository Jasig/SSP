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

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO.CoachPersonLiteTONameComparator;

public class RecentActivityTO {
	
	public static class RecentActivityTOActivityDateComparator implements Comparator<RecentActivityTO> {
		 @Override
		    public int compare(RecentActivityTO o1, RecentActivityTO o2) {
		        return o2.getActivityDate().compareTo(o1.getActivityDate());
		    }

	}

	public static final RecentActivityTOActivityDateComparator RECENT_ACTIVITY_TO_DATE_COMPARATOR =
			new RecentActivityTOActivityDateComparator();
	
	private String coachName;
	private String activity;
	private Date activityDate;
	private UUID coachId;
	/**
	 * @param coachName
	 * @param activity
	 * @param activityDate
	 */
	public RecentActivityTO(UUID coachId, String coachName, String activity, Date activityDate) {
		super();
		this.coachName = coachName;
		this.activity = activity;
		this.activityDate = activityDate;
		this.coachId = coachId;
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
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}
	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}
	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	/**
	 * @return the coachId
	 */
	public UUID getCoachId() {
		return coachId;
	}
	/**
	 * @param coachId the coachId to set
	 */
	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	} 

}
