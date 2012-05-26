package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/**
 * EarlyAlertReferral reference object.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EarlyAlertReferral
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -1740796259941040664L;

	@Column(nullable = false)
	@NotNull
	private short sortOrder = 0; // NOPMD by jon.adams on 5/4/12 1:43 PM

	@Column(nullable = false)
	@NotNull
	private String acronym;

	/**
	 * Constructor
	 */
	public EarlyAlertReferral() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public EarlyAlertReferral(@NotNull final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 * @param description
	 *            Description; max 64000 characters
	 * @param sortOrder
	 *            Default sort order when displaying objects to the user
	 * @param acronym
	 *            acronym (a.k.a. code)
	 */
	public EarlyAlertReferral(@NotNull final UUID id,
			@NotNull final String name,
			final String description, final short sortOrder, // NOPMD by jon
			final String acronym) {
		super(id, name, description);
		this.sortOrder = sortOrder;
		this.acronym = acronym;
	}

	/**
	 * Gets the default sort order when displaying an item list to the user
	 * 
	 * @return the sortOrder
	 */
	public short getSortOrder() { // NOPMD by jon.adams on 5/4/12 1:43 PM
		return sortOrder;
	}

	/**
	 * Sets the default sort order when displaying an item list to the user
	 * 
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(final short sortOrder) { // NOPMD by jon on 5/4/12
														// 11:16
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the acronym (a.k.a. code)
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym
	 *            the acronym (a.k.a. code) to set
	 */
	public void setAcronym(@NotNull final String acronym) {
		this.acronym = acronym;
	}

	/**
	 * Unique (amongst all Models in the system) prime for use by
	 * {@link #hashCode()}
	 */
	@Override
	protected int hashPrime() {
		return 157;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= super.hashCode();

		result *= hashField("sortOrder", sortOrder);
		result *= hashField("acronym", acronym);

		return result;
	}
}