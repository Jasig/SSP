package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jasig.ssp.model.external.Term;

import com.google.common.collect.Lists;

public class TermTO implements ExternalDataTO<Term> {

	private String code;

	private String name;

	private Date startDate;

	private Date endDate;

	private int reportYear;

	public TermTO() {
		super();
	}

	public TermTO(final Term model) {
		super();
		from(model);
	}

	@Override
	public final void from(final Term model) {
		code = model.getCode();
		name = model.getName();
		setStartDate(model.getStartDate());
		setEndDate(model.getEndDate());
		reportYear = model.getReportYear();
	}

	public static List<TermTO> toTOList(
			final Collection<Term> models) {
		final List<TermTO> tObjects = Lists.newArrayList();
		for (final Term model : models) {
			tObjects.add(new TermTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

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

	public final void setStartDate(final Date startDate) {
		this.startDate = (startDate == null) ? null : new Date(
				startDate.getTime());
	}

	public Date getEndDate() {
		return (endDate == null) ? null : new Date(endDate.getTime());
	}

	public final void setEndDate(final Date endDate) {
		this.endDate = (endDate == null) ? null : new Date(endDate.getTime());
	}

	public int getReportYear() {
		return reportYear;
	}

	public void setReportYear(final int reportYear) {
		this.reportYear = reportYear;
	}

	@Override
	public Serializable getId() {
		return code;
	}

	/**
	 * Sets the identifier.
	 * 
	 * @throws ClassCastException
	 *             if the specified identifier is not of the expected type.
	 */
	@Override
	public void setId(final Serializable id) {
		if (!(id instanceof String)) {
			throw new ClassCastException(
					"Can not cast identifier to the expected type. Id: " + id);
		}

		code = (String) id;
	}
}