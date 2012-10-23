package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/**
 * PersonalityType reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonalityType
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 20121004154130425L;

	/**
	 * Constructor
	 */
	public PersonalityType() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public PersonalityType(@NotNull final UUID id) {
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

	public PersonalityType(@NotNull final UUID id, @NotNull final String name) {
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
	public PersonalityType(@NotNull final UUID id, @NotNull final String name,
			final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 397;
	}

	// default hashCode okay if no extra fields are added
}
