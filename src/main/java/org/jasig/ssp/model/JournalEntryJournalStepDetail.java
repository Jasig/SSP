package org.jasig.ssp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.reference.JournalStepDetail;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalEntryJournalStepDetail extends Auditable implements
		Serializable {

	private static final long serialVersionUID = -1482715931640054820L;

	@ManyToOne()
	@JoinColumn(name = "journal_entry_id", nullable = false)
	private JournalEntry journalEntry;

	@ManyToOne()
	@JoinColumn(name = "journal_step_detail_id", nullable = false)
	private JournalStepDetail journalStepDetail;

	public JournalEntry getJournalEntry() {
		return journalEntry;
	}

	public void setJournalEntry(final JournalEntry journalEntry) {
		this.journalEntry = journalEntry;
	}

	public JournalStepDetail getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(final JournalStepDetail journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

	@Override
	protected int hashPrime() {
		return 257;
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		result *= journalEntry == null ? "journalEntry".hashCode()
				: journalEntry
						.hashCode();
		result *= journalStepDetail == null ? "journalStepDetail".hashCode()
				: journalStepDetail.hashCode();

		return result;
	}

}
