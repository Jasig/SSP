package org.studentsuccessplan.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.studentsuccessplan.ssp.model.reference.Challenge;

/**
 * Students may have zero or multiple Challenges in their way to success.
 * 
 * The PersonChallenge entity is an associative mapping between a student
 * (Person) and any Challenges they face.
 * 
 * Non-student users should never have any assigned Challenges.
 * 
 * @author jon.adams
 */
@Entity
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
		super();
	}

	public PersonChallenge(Person person, Challenge challenge) {
		super();
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
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(PersonChallenge source) {
		this.setDescription(source.getDescription());
	}
}
