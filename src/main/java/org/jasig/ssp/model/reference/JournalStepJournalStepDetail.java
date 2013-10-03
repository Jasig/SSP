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
package org.jasig.ssp.model.reference;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.Auditable;

/**
 * JournalStep JournalStepDetail model
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStepJournalStepDetail extends AbstractAuditable implements
		Auditable {

	private static final long serialVersionUID = -692899843955375605L;

	@ManyToOne
	@JoinColumn(name = "journal_step_id", nullable = false)
	private JournalStep journalStep;

	@ManyToOne
	@JoinColumn(name = "journal_step_detail_id", nullable = false)
	private JournalStepDetail journalStepDetail;
	
	@NotNull
	private Integer sortOrder;

	public JournalStep getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(final JournalStep journalStep) {
		this.journalStep = journalStep;
	}

	public JournalStepDetail getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(final JournalStepDetail journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

	@Override
	protected int hashPrime() {
		return 191;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:29 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("journalStepDetail", journalStepDetail);
		result *= hashField("journalStep", journalStep);

		return result;
	}

}