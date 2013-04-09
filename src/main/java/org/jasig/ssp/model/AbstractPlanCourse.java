package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class AbstractPlanCourse extends AbstractAuditable implements Cloneable {

	private static final long serialVersionUID = 4387422660830382586L;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)
	private String termCode;
	
	@Column(length = 200 , nullable = false)
	@Size(max = 200)	
	private String courseCode;
	
	@Column(length = 200 , nullable = false)
	@Size(max = 200)
	private String formattedCourse;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)	
	private String courseTitle;
	
	@Column(length = 200, nullable = false)
	@Size(max = 200)	
	private String courseDescription;
	
	@Column(nullable = false)
	private Integer creditHours;
	
	@Column(nullable = false)
	private Boolean isDev;

	@Column(nullable = false)
	private Integer orderInTerm;
	
	@Override
	protected int hashPrime() {
		return 0;
	}

	@Override
	public int hashCode() {
		return 0;
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
