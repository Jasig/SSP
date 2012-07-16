package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RegistrationStatusByTerm external data object.
 */
public class RegistrationStatusByTerm implements Serializable {

	private static final long serialVersionUID = -2034426964159477894L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;

	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;

	@NotNull
	@Column(nullable = false, length = 25)
	private int registeredCourseCount;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	public int getRegisteredCourseCount() {
		return registeredCourseCount;
	}

	public void setRegisteredCourseCount(final int registeredCourseCount) {
		this.registeredCourseCount = registeredCourseCount;
	}

}
