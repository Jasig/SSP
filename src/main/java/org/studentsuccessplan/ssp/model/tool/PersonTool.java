package edu.sinclair.ssp.model.tool;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.sinclair.ssp.model.Auditable;
import edu.sinclair.ssp.model.Person;

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

	public PersonTool(Person person, Tools tool) {
		super();
		this.person = person;
		this.tool = tool;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Tools getTool() {
		return tool;
	}

	public void setTool(Tools tool) {
		this.tool = tool;
	}

}
