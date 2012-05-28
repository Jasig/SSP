package org.jasig.ssp.model.reference;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStepJournalStepDetail
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -692899843955375605L;

	@ManyToOne
	@JoinColumn(name = "journal_step_id", nullable = false)
	private JournalStep journalStep;

	@ManyToOne
	@JoinColumn(name = "journal_step_detail_id", nullable = false)
	private JournalStepDetail journalStepDetail;

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