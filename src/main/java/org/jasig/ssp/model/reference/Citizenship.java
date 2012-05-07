package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

/**
 * Citizenship reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Citizenship extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 1098149686013321936L;

	/**
	 * Constructor
	 */
	public Citizenship() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Citizenship(@NotNull UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 80 characters
	 */

	public Citizenship(@NotNull UUID id, @NotNull String name) {
		super(id, name);
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
	 */
	public Citizenship(@NotNull UUID id, @NotNull String name,
			String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 67;
	};
}
