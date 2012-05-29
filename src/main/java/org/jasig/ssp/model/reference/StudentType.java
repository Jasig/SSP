package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * StudentType reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class StudentType
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -7875126705128856132L;

	private boolean requireInitialAppointment;

	/**
	 * Empty constructor
	 */
	public StudentType() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public StudentType(final UUID id) {
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

	public StudentType(final UUID id, final String name) {
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
	public StudentType(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	/**
	 * @return the requireInitialAppointment
	 */
	public boolean isRequireInitialAppointment() {
		return requireInitialAppointment;
	}

	/**
	 * @param requireInitialAppointment
	 *            the requireInitialAppointment to set
	 */
	public void setRequireInitialAppointment(
			final boolean requireInitialAppointment) {
		this.requireInitialAppointment = requireInitialAppointment;
	}

	@Override
	protected int hashPrime() {
		return 313;
	}

	@Override
	public int hashCode() {
		return hashPrime() * super.hashCode()
				* (requireInitialAppointment ? 3 : 5);
	}
}