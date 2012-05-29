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
public class JournalTrackJournalStep
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -2773118996940870207L;

	@ManyToOne
	@JoinColumn(name = "journal_track_id", nullable = false)
	private JournalTrack journalTrack;

	@ManyToOne
	@JoinColumn(name = "journal_step_id", nullable = false)
	private JournalStep journalStep;

	public JournalTrack getJournalTrack() {
		return journalTrack;
	}

	public void setJournalTrack(final JournalTrack journalTrack) {
		this.journalTrack = journalTrack;
	}

	public JournalStep getJournalStep() {
		return journalStep;
	}

	public void setJournalStep(final JournalStep journalStep) {
		this.journalStep = journalStep;
	}

	@Override
	protected int hashPrime() {
		return 251;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:33 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("journalTrack", journalTrack);
		result *= hashField("journalStep", journalStep);

		return result;
	}
}