package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * JournalSource reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalSource
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 1586445096161244662L;

	/**
	 * JournalSource EarlyAlert identifier
	 */
	public static final UUID JOURNALSOURCE_EARLYALERT_ID = UUID
			.fromString("b2d07a00-5056-a51a-80b5-f725f1c5c3e2");

	/**
	 * Empty constructor
	 */
	public JournalSource() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalSource(final UUID id) {
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

	public JournalSource(final UUID id, final String name) {
		super(id, name);
	}

	@Override
	protected int hashPrime() {
		return 211;
	}

	// default hashCode okay if no extra fields are added
}