package edu.sinclair.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.Challenge;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonChallenge extends Auditable implements Serializable {

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
	private Challenge challenge;

	public PersonChallenge() {
	}

	public PersonChallenge(Person person, Challenge challenge) {
		this.person = person;
		this.challenge = challenge;
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

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	/**
	 * Overwrites simple properties with the parameter's properties. Does not
	 * include the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithPersonAndCollections(PersonChallenge)
	 */
	public void overwrite(PersonChallenge source) {
		this.setDescription(source.getDescription());
	}
}
