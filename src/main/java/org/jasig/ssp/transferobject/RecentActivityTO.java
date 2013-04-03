package org.jasig.ssp.transferobject;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO.CoachPersonLiteTONameComparator;

public class RecentActivityTO {
	
	public static class RecentActivityTOActivityDateComparator implements Comparator<RecentActivityTO> {
		 @Override
		    public int compare(RecentActivityTO o1, RecentActivityTO o2) {
		        return o1.getActivityDate().compareTo(o2.getActivityDate());
		    }

	}

	public static final RecentActivityTOActivityDateComparator RECENT_ACTIVITY_TO_DATE_COMPARATOR =
			new RecentActivityTOActivityDateComparator();
	
	private String coachName;
	private String activity;
	private Date activityDate;
	/**
	 * @param coachName
	 * @param activity
	 * @param activityDate
	 */
	public RecentActivityTO(String coachName, String activity, Date activityDate) {
		super();
		this.coachName = coachName;
		this.activity = activity;
		this.activityDate = activityDate;
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

}
