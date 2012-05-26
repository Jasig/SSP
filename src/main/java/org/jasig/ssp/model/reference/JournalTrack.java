package org.jasig.ssp.model.reference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.jasig.ssp.model.Auditable;

/**
 * JournalTrack reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalTrack
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 2719277716161933677L;

	public static final UUID JOURNALTRACK_EARLYALERT_ID = UUID
			.fromString("B2D07B38-5056-A51A-809D-81EA2F3B27BF");

	private int sortOrder;

	/**
	 * Journal steps. Changes to this side of the relationship are not
	 * persisted.
	 */
	@OneToMany(mappedBy = "journalTrack")
	private Set<JournalTrackJournalStep> journalTrackJournalSteps = new HashSet<JournalTrackJournalStep>(
			0);

	/**
	 * Empty constructor
	 */
	public JournalTrack() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalTrack(final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public JournalTrack(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Gets the sort order
	 * 
	 * @return the sort order
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Set<JournalTrackJournalStep> getJournalTrackJournalSteps() {
		return journalTrackJournalSteps;
	}

	public void setJournalTrackJournalSteps(
			final Set<JournalTrackJournalStep> journalTrackJournalSteps) {
		this.journalTrackJournalSteps = journalTrackJournalSteps;
	}

	@Override
	protected int hashPrime() {
		return 227;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* hashField("sortOrder", sortOrder);
	}
}