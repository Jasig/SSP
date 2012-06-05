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
