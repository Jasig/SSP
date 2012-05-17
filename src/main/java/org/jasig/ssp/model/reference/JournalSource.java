package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * JournalSource reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalSource extends AbstractReference implements
		Serializable {

	private static final long serialVersionUID = 1586445096161244662L;

	/**
	 * Constructor
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
}