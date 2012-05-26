package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * Ethnicity reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ethnicity
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -2076285639616178551L;

	/**
	 * Constructor
	 */
	public Ethnicity() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Ethnicity(final UUID id) {
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

	public Ethnicity(final UUID id, final String name) {
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
	public Ethnicity(final UUID id, final String name, final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 101;
	}

	// default hashCode okay if no extra fields are added
}