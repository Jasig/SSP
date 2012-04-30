package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Goal reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Goal extends AbstractReference implements
		Serializable {

	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "confidentiality_level_id", unique = true, nullable = false)
	private ConfidentialityLevel confidentialityLevel;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Goal() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Goal(UUID id) {
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

	public Goal(UUID id, String name) {
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
	public Goal(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	@Override
	protected int hashPrime() {
		return 107;
	};
}
