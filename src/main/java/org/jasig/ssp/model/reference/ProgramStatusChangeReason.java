package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * ProgramStatusChangeReason reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProgramStatusChangeReason
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -4811273185578231919L;

	public static final UUID UNKNOWN_ID = UUID
			.fromString("b2d1290F-5056-a51a-8094-3b321a901899");

	/**
	 * Constructor
	 */
	public ProgramStatusChangeReason() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ProgramStatusChangeReason(final UUID id) {
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

	public ProgramStatusChangeReason(final UUID id, final String name) {
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
	public ProgramStatusChangeReason(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 311;
	}

	// default hashCode okay if no extra fields are added
}