/**
 * 
 */
package org.jasig.ssp.transferobject.external;

import java.io.Serializable;

import org.jasig.ssp.model.external.ExternalStudentsByCourse;

/**
 * @author jamesstanley
 *
 */
public class ExternalStudentsByCourseTO implements ExternalDataTO<ExternalStudentsByCourse>,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3990101735664349552L;
	
	private String schoolId;
	private String formattedCourse;
	private String termCode;
	private String firstName;
	private String middleName;
	private String lastName;
	private String audited;
	private String academicGrade;
	
	/**
	 * 
	 */
	public ExternalStudentsByCourseTO() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param schoolId
	 * @param formattedCourse
	 * @param termCode
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param audited
	 * @param academicGrade
	 */
	public ExternalStudentsByCourseTO(String schoolId, String formattedCourse,
			String termCode, String firstName, String middleName,
			String lastName, String audited, String academicGrade) {
		super();
		this.schoolId = schoolId;
		this.formattedCourse = formattedCourse;
		this.termCode = termCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.audited = audited;
		this.academicGrade = academicGrade;
	}
	
	@Override
	public void from(ExternalStudentsByCourse model) {
		schoolId = model.getSchoolId();
		formattedCourse = model.getFormattedCourse();
		termCode = model.getTermCode();
		firstName = model.getFirstName();
		middleName = model.getMiddleName();
		lastName = model.getLastName();
		audited = model.getAudited();
		academicGrade = model.getAcademicGrade();
		
	}
	
	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the formattedCourse
	 */
	public String getFormattedCourse() {
		return formattedCourse;
	}

	/**
	 * @param formattedCourse the formattedCourse to set
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the audited
	 */
	public String getAudited() {
		return audited;
	}

	/**
	 * @param audited the audited to set
	 */
	public void setAudited(final String audited) {
		this.audited = audited;
	}

	/**
	 * @return the academicGrade
	 */
	public String getAcademicGrade() {
		return academicGrade;
	}

	/**
	 * @param academicGrade the academicGrade to set
	 */
	public void setAcademicGrade(final String academicGrade) {
		this.academicGrade = academicGrade;
	}
	
	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}


}
