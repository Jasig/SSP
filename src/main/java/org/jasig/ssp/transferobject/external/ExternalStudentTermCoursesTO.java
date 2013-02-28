package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.List;

import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTermCourses;

public class ExternalStudentTermCoursesTO implements ExternalDataTO<ExternalStudentTermCourses>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1802980832496414863L;
	
	String code;
	List<ExternalStudentTranscriptCourseTO> courses;
	
	public ExternalStudentTermCoursesTO(ExternalStudentTermCourses model){
		from(model);
	}
	
	/**
	 * 
	 */
	@Override
	public void from(ExternalStudentTermCourses model) {
		code = model.getCode();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the courses
	 */
	public List<ExternalStudentTranscriptCourseTO> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<ExternalStudentTranscriptCourseTO> courses) {
		this.courses = courses;
	}

}
