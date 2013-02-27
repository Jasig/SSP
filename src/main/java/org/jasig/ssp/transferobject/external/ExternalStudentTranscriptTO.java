package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.math.BigDecimal;

import org.jasig.ssp.model.external.ExternalStudentTranscript;

public class ExternalStudentTranscriptTO implements ExternalDataTO<ExternalStudentTranscript>,
		Serializable {
	

	private String schoolId;
	private BigDecimal creditHoursForGpa;
	private BigDecimal creditHoursAttempted;
	private BigDecimal totalQualityPoints;
	private BigDecimal gradePointAverrage;

	@Override
	public void from(ExternalStudentTranscript model) {
		
		schoolId = model.getSchoolId();
		creditHoursForGpa = model.getCreditHoursForGpa();
		creditHoursAttempted = model.getCreditHoursAttempted();
		totalQualityPoints = model.getTotalQualityPoints();
		gradePointAverrage = model.getGradePointAverrage();
	}

	/**
	 * @param schoolId
	 * @param creditHoursForGpa
	 * @param creditHoursAttempted
	 * @param totalQualityPoints
	 * @param gradePointAverrage
	 */
	public ExternalStudentTranscriptTO(final String schoolId,
			final BigDecimal creditHoursForGpa, final BigDecimal creditHoursAttempted,
			final BigDecimal totalQualityPoints, final BigDecimal gradePointAverrage) {
		super();
		this.schoolId = schoolId;
		this.creditHoursForGpa = creditHoursForGpa;
		this.creditHoursAttempted = creditHoursAttempted;
		this.totalQualityPoints = totalQualityPoints;
		this.gradePointAverrage = gradePointAverrage;
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
	 * @return the creditHoursForGpa
	 */
	public BigDecimal getCreditHoursForGpa() {
		return creditHoursForGpa;
	}

	/**
	 * @param creditHoursForGpa the creditHoursForGpa to set
	 */
	public void setCreditHoursForGpa(final BigDecimal creditHoursForGpa) {
		this.creditHoursForGpa = creditHoursForGpa;
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
	public void setCreditHoursAttempted(final BigDecimal creditHoursAttempted) {
		this.creditHoursAttempted = creditHoursAttempted;
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
	public void setTotalQualityPoints(final BigDecimal totalQualityPoints) {
		this.totalQualityPoints = totalQualityPoints;
	}

	/**
	 * @return the gradePointAverrage
	 */
	public BigDecimal getGradePointAverrage() {
		return gradePointAverrage;
	}

	/**
	 * @param gradePointAverrage the gradePointAverrage to set
	 */
	public void setGradePointAverrage(final BigDecimal gradePointAverrage) {
		this.gradePointAverrage = gradePointAverrage;
	}

}
