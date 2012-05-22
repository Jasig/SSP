package org.jasig.ssp.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.ConfidentialityLevel;

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
	public Goal(final UUID id) {
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
	public Goal(final UUID id, final String name) {
		super(id, name);
	}

	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	@Override
	protected int hashPrime() {
		return 107;
	};

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime() * super.hashCode();

		result *= confidentialityLevel == null ? "confidentialityLevel"
				.hashCode() : confidentialityLevel.getId().hashCode();

		return result;
	}
}
