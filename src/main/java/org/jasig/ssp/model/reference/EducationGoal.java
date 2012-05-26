package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;

/**
 * EducationGoal reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationGoal
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -306887708908281830L;

	@Column(length = 255)
	@Size(max = 255)
	private String otherDescription;

	/**
	 * Constructor
	 */
	public EducationGoal() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public EducationGoal(final UUID id) {
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

	public EducationGoal(final UUID id, final String name) {
		super(id, name);
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	/**
	 * Set other description. Maximum 255 characters.
	 * 
	 * @param otherDescription
	 *            Maximum 255 characters.
	 */
	public void setOtherDescription(final String otherDescription) {
		this.otherDescription = otherDescription;
	}

	@Override
	protected int hashPrime() {
		return 83;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* hashField("otherDescription", otherDescription);
	}
}