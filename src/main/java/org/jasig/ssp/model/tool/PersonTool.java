package org.jasig.ssp.model.tool;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.Person;

/**
 * PersonTool model
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonTool extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@Enumerated(EnumType.STRING)
	private Tools tool;

	public PersonTool() {
		super();
	}

	public PersonTool(final Person person, final Tools tool) {
		super();
		this.person = person;
		this.tool = tool;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	public Tools getTool() {
		return tool;
	}

	public void setTool(final Tools tool) {
		this.tool = tool;
	}

	@Override
	protected int hashPrime() {
		return 41;
	};

	@Override
	final public int hashCode() {
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonTool
		result *= person == null || person.getId() == null ? "person"
				.hashCode() : person.getId().hashCode();
		result *= tool == null ? "tool".hashCode() : tool.hashCode();

		return result;
	}
}
