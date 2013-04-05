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
@Table(name = "v_external_student_transcript_term")
public class ExternalStudentTranscriptTerm extends AbstractExternalData
		implements Serializable, ExternalData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8234383994143195272L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	String schoolId;
	
	@Column(nullable = true)
	BigDecimal creditHoursForGpa;
	
	@Column(nullable = true)
	BigDecimal creditHoursEarned;
	
	@Column(nullable = true)
	BigDecimal creditHoursAttempted;
	
	@Column(nullable = true)
	BigDecimal creditHoursNotCompleted;
	
	@Column(nullable = true)
	BigDecimal creditCompletionRate;
	
	@Column(nullable = true)
	BigDecimal totalQualityPoints;
	
	@Column(nullable = false)
	BigDecimal gradePointAverage;
	
	@Column(nullable = false, length = 25)
	@Size(max = 25)
	private String termCode;

	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the creditHoursForGpa
	 */
	public BigDecimal getCreditHoursForGpa() {
		return creditHoursForGpa;
	}

	/**
	 * @param creditHoursForGpa the creditHoursForGpa to set
	 */
	public void setCreditHoursForGpa(BigDecimal creditHoursForGpa) {
		this.creditHoursForGpa = creditHoursForGpa;
	}

	/**
	 * @return the creditHoursEarned
	 */
	public BigDecimal getCreditHoursEarned() {
		return creditHoursEarned;
	}

	/**
	 * @param creditHoursEarned the creditHoursEarned to set
	 */
	public void setCreditHoursEarned(BigDecimal creditHoursEarned) {
		this.creditHoursEarned = creditHoursEarned;
	}

	/**
	 * @return the creditHoursAttempted
	 */
	public BigDecimal getCreditHoursAttempted() {
		return creditHoursAttempted;
	}

	/**
	 * @param creditHoursAttempted the creditHoursAttempted to set
	 */
	public void setCreditHoursAttempted(BigDecimal creditHoursAttempted) {
		this.creditHoursAttempted = creditHoursAttempted;
	}

	/**
	 * @return the creditHoursNotCompleted
	 */
	public BigDecimal getCreditHoursNotCompleted() {
		return creditHoursNotCompleted;
	}

	/**
	 * @param creditHoursNotCompleted the creditHoursNotCompleted to set
	 */
	public void setCreditHoursNotCompleted(BigDecimal creditHoursNotCompleted) {
		this.creditHoursNotCompleted = creditHoursNotCompleted;
	}

	/**
	 * @return the creditCompletionRate
	 */
	public BigDecimal getCreditCompletionRate() {
		return creditCompletionRate;
	}

	/**
	 * @param creditCompletionRate the creditCompletionRate to set
	 */
	public void setCreditCompletionRate(BigDecimal creditCompletionRate) {
		this.creditCompletionRate = creditCompletionRate;
	}

	/**
	 * @return the totalQualityPoints
	 */
	public BigDecimal getTotalQualityPoints() {
		return totalQualityPoints;
	}

	/**
	 * @param totalQualityPoints the totalQualityPoints to set
	 */
	public void setTotalQualityPoints(BigDecimal totalQualityPoints) {
		this.totalQualityPoints = totalQualityPoints;
	}

	/**
	 * @return the gradePointAverage
	 */
	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	/**
	 * @param gradePointAverage the gradePointAverage to set
	 */
	public void setGradePointAverage(BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
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
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

}
