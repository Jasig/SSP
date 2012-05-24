package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.reference.Challenge;

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
public class PersonChallenge
		extends AbstractAuditable
		implements PersonAssocAuditable {

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

	public PersonChallenge(final Person person, final Challenge challenge) {
		super();
		this.person = person;
		this.challenge = challenge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(final Challenge challenge) {
		this.challenge = challenge;
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(final PersonChallenge source) {
		this.setDescription(source.getDescription());
	}

	@Override
	protected int hashPrime() {
		return 5;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:15 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonChallenge
		result *= StringUtils.isEmpty(description) ? "description".hashCode()
				: description.hashCode();
		result *= (person == null) || (person.getId() == null) ? "person"
				.hashCode() : person.getId().hashCode();
		result *= challenge == null ? "challenge".hashCode() : challenge
				.hashCode();

		return result;
	}
}
