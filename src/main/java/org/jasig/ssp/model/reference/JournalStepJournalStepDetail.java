package org.jasig.ssp.model.reference;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStepJournalStepDetail extends Auditable implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "journal_step_id", nullable = false)
	private JournalStep journalStep;

	@ManyToOne
	@JoinColumn(name = "journal_step_detail_id", nullable = false)
	private JournalStepDetail journalStepDetail;

	public JournalStep getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(JournalStep journalStep) {
		this.journalStep = journalStep;
	}

	public JournalStepDetail getJournalStepDetail() {
		return journalStepDetail;
	}

	public void setJournalStepDetail(JournalStepDetail journalStepDetail) {
		this.journalStepDetail = journalStepDetail;
	}

	@Override
	protected int hashPrime() {
		return 191;
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		result *= journalStepDetail == null ? "journalStepDetail".hashCode()
				: journalStepDetail
						.hashCode();
		result *= journalStep == null ? "journalStep".hashCode()
				: journalStep.hashCode();

		return result;
	}

}
