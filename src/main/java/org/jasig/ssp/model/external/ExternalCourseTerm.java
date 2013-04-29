package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_course_term")
public class ExternalCourseTerm extends AbstractExternalData implements ExternalData, Serializable {

	private static final long serialVersionUID = 1529384456357160956L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String courseCode;

	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;
	

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	
}
