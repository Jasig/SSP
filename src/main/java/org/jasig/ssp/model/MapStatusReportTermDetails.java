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
package org.jasig.ssp.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MapStatusReportTermDetails extends AbstractAuditable implements Auditable  {

	private static final long serialVersionUID = -8908014739182627558L;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "report_id", updatable = false, nullable = false)
	private MapStatusReport report;
	
	@Column(length = 25)
	@Size(max = 25)
	private String termCode;
	
	
	@Column(length = 500)
	@Size(max = 500)
	private String anomalyNote;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TermStatus termStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AnomalyCode anomalyCode;
	
	@Column(precision = 2, scale = 2, nullable = false)
	private BigDecimal termRatio;

	public MapStatusReportTermDetails(UUID id) {
		super();
		setId(id);
	}

	public MapStatusReportTermDetails() {
		super();
	}

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAnomalyNote() {
		return anomalyNote;
	}

	public void setAnomalyNote(String anomalyNote) {
		this.anomalyNote = anomalyNote;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public MapStatusReport getReport() {
		return report;
	}

	public void setReport(MapStatusReport report) {
		this.report = report;
	}

	public AnomalyCode getAnomalyCode() {
		return anomalyCode;
	}

	public void setAnomalyCode(AnomalyCode anomalyCode) {
		this.anomalyCode = anomalyCode;
	}

	public TermStatus getTermStatus() {
		return termStatus;
	}

	public void setTermStatus(TermStatus termStatus) {
		this.termStatus = termStatus;
	}

	public BigDecimal getTermRatio() {
		return termRatio;
	}

	public void setTermRatio(BigDecimal termRatio) {
		this.termRatio = termRatio;
	}


}
