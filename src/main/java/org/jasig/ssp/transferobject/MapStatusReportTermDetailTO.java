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
package org.jasig.ssp.transferobject;

import java.math.BigDecimal;

import org.jasig.ssp.model.MapStatusReportTermDetails;

/**
 */
public class MapStatusReportTermDetailTO extends
AbstractAuditableTO<MapStatusReportTermDetails> implements TransferObject<MapStatusReportTermDetails> {

	private String termCode;
	private String anomalyNote;
	private String anomalyCode;
	private String termRatio;
	
	/**
	 * Empty constructor.
	 */
	public MapStatusReportTermDetailTO() {
		super();
	}
	/**
	 * Empty constructor.
	 */
	public MapStatusReportTermDetailTO(MapStatusReportTermDetails model) {
		super();
		from(model);
	}
	

	public void from(MapStatusReportTermDetails model) {
		super.from(model);
		this.setTermCode(model.getTermCode());
		this.setAnomalyCode(model.getAnomalyCode().getDisplayText());
		this.setTermRatio(model.getTermRatio().multiply(new BigDecimal(100))+"%");
		this.setAnomalyNote(model.getAnomalyNote());
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getAnomalyNote() {
		return anomalyNote;
	}
	public void setAnomalyNote(String anomalyNote) {
		this.anomalyNote = anomalyNote;
	}
	public String getAnomalyCode() {
		return anomalyCode;
	}
	public void setAnomalyCode(String anomalyCode) {
		this.anomalyCode = anomalyCode;
	}
	public String getTermRatio() {
		return termRatio;
	}
	public void setTermRatio(String termRatio) {
		this.termRatio = termRatio;
	}

}