package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Category reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 4274075255831698154L;

	/**
	 * Constructor
	 */
	public Category() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public Category(final UUID id) {
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

	public Category(final UUID id, final String name) {
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
	public Category(final UUID id, final String name, final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 43;
	};
}
