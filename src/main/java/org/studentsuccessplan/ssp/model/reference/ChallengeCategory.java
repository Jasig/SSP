package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * ChallengeCategory reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeCategory extends AbstractReference implements
		Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ChallengeCategory() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ChallengeCategory(UUID id) {
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

	public ChallengeCategory(UUID id, String name) {
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
	public ChallengeCategory(UUID id, String name, String description) {
		super(id, name, description);
	}
}
