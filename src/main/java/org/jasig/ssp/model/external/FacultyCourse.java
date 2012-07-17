package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * FacultyCourse external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_faculty_course")
public class FacultyCourse extends AbstractExternalData implements
		Serializable, ExternalData {

	private static final long serialVersionUID = 5820559696467468171L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String facultySchoolId;

	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;

	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String formattedCourse;

	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String title;

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	/**
	 * @param facultySchoolId
	 *            the facultySchoolId
	 */
	public void setFacultySchoolId(final String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode
	 *            the termCode
	 */
	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	/**
	 * @param formattedCourse
	 *            the formattedCourse
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
}