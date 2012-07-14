package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Term external data object.
 */
public class Term implements Serializable {

	private static final long serialVersionUID = 7709074056601029932L;

	@Column(nullable = false, length = 80)
	@NotNull
	@NotEmpty
	@Size(max = 80)
	private String name;

	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endDate;

	@NotNull
	@Column(nullable = false, length = 25)
	private int reportYear;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return (startDate == null) ? null : new Date(startDate.getTime());
	}

	public void setStartDate(final Date startDate) {
		this.startDate = (startDate == null) ? null : new Date(
				startDate.getTime());
	}

	public Date getEndDate() {
		return (endDate == null) ? null : new Date(endDate.getTime());
	}

	public void setEndDate(final Date endDate) {
		this.endDate = (endDate == null) ? null : new Date(endDate.getTime());
	}

	public int getReportYear() {
		return reportYear;
	}

	public void setReportYear(final int reportYear) {
		this.reportYear = reportYear;
	}
}