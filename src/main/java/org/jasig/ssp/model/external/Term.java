/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Term external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_term")
public class Term extends AbstractExternalReferenceData implements Serializable,
		ExternalData {

	private static final long serialVersionUID = 7709074056601029932L;

	@Column(nullable = false, length = 80)
	@NotNull
	@NotEmpty
	@Size(max = 80)
	private String name;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endDate;

	@NotNull
	@Column(nullable = false, length = 25)
	private int reportYear;

	public Term() {
		super();
	}

	public Term(final String code) {
		super();
		this.setCode(code);
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate == null ? null : new Date(startDate.getTime());
	}

	/**
	 * @param startDate
	 *            the start date
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate == null ? null : new Date(
				startDate.getTime());
	}

	public Date getEndDate() {
		return endDate == null ? null : new Date(endDate.getTime());
	}

	/**
	 * @param endDate
	 *            the end date
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate == null ? null : new Date(endDate.getTime());
	}

	public int getReportYear() {
		return reportYear;
	}

	/**
	 * @param reportYear
	 *            the report year
	 */
	public void setReportYear(final int reportYear) {
		this.reportYear = reportYear;
	}
}