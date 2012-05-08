package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 * EducationGoal reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationGoal extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 1L;

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
	};

	@Override
	public int hashCode() {
		int result = hashPrime() * super.hashCode();

		result *= otherDescription == null ? "otherDescription".hashCode()
				: otherDescription.hashCode();

		return result;
	}
}
