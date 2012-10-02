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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalEntryDetail
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = 8777376050728633364L;

	@ManyToOne()
	@JoinColumn(name = "journal_entry_id", nullable = false, updatable = false)
	private JournalEntry journalEntry;

	@ManyToOne
	@JoinColumn(name = "journal_step_journal_step_detail_id", nullable = false, updatable = false)
	private JournalStepJournalStepDetail journalStepJournalStepDetail;

	public JournalEntry getJournalEntry() {
		return journalEntry;
	}

	public void setJournalEntry(@NotNull final JournalEntry journalEntry) {
		this.journalEntry = journalEntry;
	}

	public JournalStepJournalStepDetail getJournalStepJournalStepDetail() {
		return journalStepJournalStepDetail;
	}

	public void setJournalStepJournalStepDetail(
			final JournalStepJournalStepDetail journalStepJournalStepDetail) {
		this.journalStepJournalStepDetail = journalStepJournalStepDetail;
	}

	@Override
	protected int hashPrime() {
		return 257;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:29 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("journalStepJournalStepDetail",
				journalStepJournalStepDetail);
		result *= hashField("journalEntry", journalEntry);

		return result;
	}
}
