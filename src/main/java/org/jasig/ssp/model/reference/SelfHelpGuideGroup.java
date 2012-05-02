package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * SelfHelpGuideGroup reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuideGroup extends AbstractReference implements
		Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SelfHelpGuideGroup() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public SelfHelpGuideGroup(UUID id) {
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

	public SelfHelpGuideGroup(UUID id, String name) {
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
	public SelfHelpGuideGroup(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 131;
	};
}
