package edu.sinclair.ssp.model;

import java.util.UUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.sinclair.ssp.model.reference.Challenge;

public class PersonChallenge {

	private UUID id;

	private String description;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id", nullable = false, insertable = false, updatable = false)
	private Person person;

	private Challenge challenge;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

}
