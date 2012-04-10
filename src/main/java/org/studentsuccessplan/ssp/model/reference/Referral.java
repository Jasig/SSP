package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 * Referral reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Referral extends AbstractReference implements Serializable {

	private static final long serialVersionUID = -4151683155103811191L;

	/**
	 * Public description
	 * 
	 * Optional, null allowed, max length 150 characters.
	 */
	@Column(nullable = true, length = 150)
	@Size(max = 150)
	private String publicDescription;

	/**
	 * Constructor
	 */
	public Referral() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public Referral(final UUID id) {
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

	public Referral(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 150 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public Referral(final UUID id, final String name, final String description) {
		super(id, name, description);
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
	 * @param publicDescription
	 *            Public description; max 150 characters
	 */
	public Referral(final UUID id, final String name, final String description,
			final String publicDescription) {
		super(id, name, description);
		this.publicDescription = publicDescription;
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	/**
	 * Sets the public description
	 * 
	 * @param publicDescription
	 *            Name; null allowed; max 150 characters
	 */
	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}
}
