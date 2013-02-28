package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class ExternalStudentTermCourses extends AbstractExternalData implements
ExternalData, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -180393846186163477L;
	private String code;
	private List<ExternalStudentTranscriptCourse> courses;
	
	public ExternalStudentTermCourses(ExternalStudentTranscriptCourse course){
		super();
		courses = Lists.newArrayList(course);
		code = course.getTermCode();
	}
	
	/**
	 * @return the term
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param term the term to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the courses
	 */
	public List<ExternalStudentTranscriptCourse> getCourses() {
		return courses;
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<ExternalStudentTranscriptCourse> courses) {
		this.courses = courses;
	}
	
	public void addCourse(ExternalStudentTranscriptCourse course){
		courses.add(course);
	}
}
