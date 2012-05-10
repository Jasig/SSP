package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ConfidentialityLevel reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConfidentialityLevel extends AbstractReference implements
		Serializable {

	private static final long serialVersionUID = 2346103896744918201L;

	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String acronym;

	/**
	 * Constructor
	 */
	public ConfidentialityLevel() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public ConfidentialityLevel(final UUID id) {
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
	public ConfidentialityLevel(@NotNull final UUID id,
			@NotNull final String name) {
		super(id, name);
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	@Override
	protected int hashPrime() {
		return 79;
	};

	@Override
	public int hashCode() {
		int result = hashPrime() * super.hashCode();

		result *= StringUtils.isEmpty(acronym) ? "acronym".hashCode() : acronym
				.hashCode();

		return result;
	}
}
