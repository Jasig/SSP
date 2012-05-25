package org.jasig.ssp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.reference.SpecialServiceGroup;

/**
 * Assign a Person to a SpecialServiceGroup
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonSpecialServiceGroup
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -3685614932117902730L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "special_service_group_id", updatable = false, nullable = false)
	private SpecialServiceGroup specialServiceGroup;

	public PersonSpecialServiceGroup() {
		super();
	}

	public PersonSpecialServiceGroup(final Person person,
			final SpecialServiceGroup specialServiceGroup) {
		super();
		this.person = person;
		this.specialServiceGroup = specialServiceGroup;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public SpecialServiceGroup getSpecialServiceGroup() {
		return specialServiceGroup;
	}

	public void setSpecialServiceGroup(
			final SpecialServiceGroup specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
	}

	@Override
	protected int hashPrime() {
		return 283;
	}

	@Override
	final public int hashCode() {
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonSpecialServiceGroup
		result *= (person == null) || (person.getId() == null) ? "person"
				.hashCode() : person.getId().hashCode();
		result *= specialServiceGroup == null ? "specialServiceGroup"
				.hashCode() : specialServiceGroup.hashCode();

		return result;
	}

}
