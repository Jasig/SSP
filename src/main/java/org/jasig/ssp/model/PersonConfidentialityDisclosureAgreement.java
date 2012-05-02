package org.studentsuccessplan.ssp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonConfidentialityDisclosureAgreement extends Auditable
		implements Serializable {

	private static final long serialVersionUID = 27277225191519712L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne()
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "confidentiality_disclosure_agreement_id")
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
		person = source.getPerson();
		confidentialityDisclosureAgreement = source
				.getConfidentialityDisclosureAgreement();
	}

	@Override
	protected int hashPrime() {
		return 7;
	};

	@Override
	final public int hashCode() {
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonConfidentialityDisclosureAgreement
		result *= person == null || person.getId() == null ? "person"
				.hashCode() : person.getId().hashCode();
		result *= confidentialityDisclosureAgreement == null ? "confidentialityDisclosureAgreement"
				.hashCode()
				: confidentialityDisclosureAgreement.hashCode();

		return result;
	}
}
