package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.AbstractPlanCourse;

public class AbstractPlanCourseTO<T extends AbstractPlanCourse> extends AbstractAuditableTO<T>
implements TransferObject<T> {

	private String termCode;
	
	private String courseCode;
	
	private String formattedCourse;
	
	private String courseTitle;
	
	private String courseDescription;
	
	private Integer creditHours;
	
	private Boolean isDev;
	
	private Integer orderInTerm;
	
	
	
	/**
	 * Empty constructor.
	 */
	public AbstractPlanCourseTO() {
		super();
	}

	
	@Override
	public void from(T model) {
		super.from(model);
		this.setCourseCode(model.getCourseCode());
		this.setCourseDescription(model.getCourseDescription());
		this.setCourseTitle(model.getCourseTitle());
		this.setCreditHours(model.getCreditHours());
		this.setFormattedCourse(model.getFormattedCourse());
		this.setIsDev(model.isDev());
		this.setOrderInTerm(model.getOrderInTerm());
		this.setTermCode(model.getTermCode());
	}


	public String getTermCode() {
		return termCode;
	}


	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}


	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public String getFormattedCourse() {
		return formattedCourse;
	}


	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}


	public String getCourseTitle() {
		return courseTitle;
	}


	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}


	public String getCourseDescription() {
		return courseDescription;
	}


	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}


	public Integer getCreditHours() {
		return creditHours;
	}


	public void setCreditHours(Integer creditHours) {
		this.creditHours = creditHours;
	}


	public Boolean isDev() {
		return isDev;
	}


	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}


	public Integer getOrderInTerm() {
		return orderInTerm;
	}


	public void setOrderInTerm(Integer orderInTerm) {
		this.orderInTerm = orderInTerm;
	}

}
