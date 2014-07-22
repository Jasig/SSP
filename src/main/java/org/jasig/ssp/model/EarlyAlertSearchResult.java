package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

public class EarlyAlertSearchResult {

	private Date createdDate;

	private Date closedDate;

	private Date lastResponseDate;

	private String courseTermCode;
	
	private String courseTermName;
	
	private Date courseTermStartDate;

	private String courseTitle;

	private String courseName;
	
	private UUID earlyAlertId;

	public EarlyAlertSearchResult() {

	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public EarlyAlertStatus getStatus() {
		return closedDate == null ? EarlyAlertStatus.OPEN : EarlyAlertStatus.CLOSED;
	}
	

	public Date getLastResponseDate() {
		return lastResponseDate;
	}

	public void setLastResponseDate(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public UUID getEarlyAlertId() {
		return earlyAlertId;
	}

	public void setEarlyAlertId(UUID earlyAlertId) {
		this.earlyAlertId = earlyAlertId;
	}

	public String getCourseTermName() {
		return courseTermName;
	}

	public void setCourseTermName(String courseTermName) {
		this.courseTermName = courseTermName;
	}
	
	public String getCourseTermCode() {
		return courseTermCode;
	}

	public void setCourseTermCode(String courseTermCode) {
		this.courseTermCode = courseTermCode;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Date getCourseTermStartDate() {
		return courseTermStartDate;
	}

	public void setCourseTermStartDate(Date courseTermStartDate) {
		this.courseTermStartDate = courseTermStartDate;
	}

}
