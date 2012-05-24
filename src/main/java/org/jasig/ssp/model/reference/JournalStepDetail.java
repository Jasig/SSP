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
 * JournalStepDetail reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalStepDetail
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5855955069509045667L;

	private int sortOrder;

	@OneToMany(mappedBy = "journalStepDetail")
	private Set<JournalStepJournalStepDetail> journalStepJournalStepDetails = new HashSet<JournalStepJournalStepDetail>(
			0);

	/**
	 * Constructor
	 */
	public JournalStepDetail() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalStepDetail(final UUID id) {
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

	public JournalStepDetail(final UUID id, final String name) {
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
	public JournalStepDetail(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
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
		return 229;
	}

	@Override
	final public int hashCode() {
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		result *= sortOrder > 0 ? sortOrder : hashPrime();

		return result;
	}
}