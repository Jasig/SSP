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
 * JournalStep reference object.
 * 
 * @author daniel.bower
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStep
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 1L;

	private int sortOrder;

	@OneToMany(mappedBy = "journalStep")
	private Set<JournalTrackJournalStep> journalTrackJournalSteps = new HashSet<JournalTrackJournalStep>(
			0);

	@OneToMany(mappedBy = "journalStep")
	private Set<JournalStepJournalStepDetail> journalStepJournalStepDetails = new HashSet<JournalStepJournalStepDetail>(
			0);

	/**
	 * Constructor
	 */
	public JournalStep() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalStep(final UUID id) {
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

	public JournalStep(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public JournalStep(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

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

	public Set<JournalStepJournalStepDetail> getJournalStepJournalStepDetails() {
		return journalStepJournalStepDetails;
	}

	public void setJournalStepJournalStepDetails(
			final Set<JournalStepJournalStepDetail> journalStepJournalStepDetails) {
		this.journalStepJournalStepDetails = journalStepJournalStepDetails;
	}

	@Override
	protected int hashPrime() {
		return 223;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		result *= sortOrder == 0 ? hashPrime() : sortOrder;

		return result;
	}
}