package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 * ConfidentialityDisclosureAgreement reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConfidentialityDisclosureAgreement extends AbstractReference
		implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * text of the agreement
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = false, length = 64000)
	@Size(max = 64000)
	private String text;

	/**
	 * Constructor
	 */
	public ConfidentialityDisclosureAgreement() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ConfidentialityDisclosureAgreement(UUID id) {
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

	public ConfidentialityDisclosureAgreement(UUID id, String name) {
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
	public ConfidentialityDisclosureAgreement(UUID id, String name,
			String description) {
		super(id, name, description);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected int hashPrime() {
		return 73;
	};
}
