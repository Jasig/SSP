package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * ConfidentialityLevel reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConfidentialityLevel extends AbstractReference implements
		Serializable {

	private static final long serialVersionUID = 1L;

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

	public ConfidentialityLevel(UUID id) {
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

	public ConfidentialityLevel(UUID id, String name) {
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
	public ConfidentialityLevel(UUID id, String name, String description) {
		super(id, name, description);
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
}
