package edu.sinclair.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.ConfidentialityDisclosureAgreement;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonConfidentialityDisclosureAgreement extends Auditable implements Serializable {

	private static final long serialVersionUID = 27277225191519712L;

	@Column(length = 255)
	@Size(max = 255)
	private String description;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "challenge_id", insertable = false, nullable = false, updatable = false)
	private ConfidentialityDisclosureAgreement confidentialityDisclosureAgreement;

	public PersonConfidentialityDisclosureAgreement() {
		super();
	}

	public PersonConfidentialityDisclosureAgreement(
			Person person,
			ConfidentialityDisclosureAgreement confidentialityDisclosureAgreement) {
		super();
		this.person = person;
		this.confidentialityDisclosureAgreement = confidentialityDisclosureAgreement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public ConfidentialityDisclosureAgreement getConfidentialityDisclosureAgreement() {
		return confidentialityDisclosureAgreement;
	}

	public void setConfidentialityDisclosureAgreement(
			ConfidentialityDisclosureAgreement confidentialityDisclosureAgreement) {
		this.confidentialityDisclosureAgreement = confidentialityDisclosureAgreement;
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(PersonConfidentialityDisclosureAgreement source) {
		this.setDescription(source.getDescription());
	}
}
