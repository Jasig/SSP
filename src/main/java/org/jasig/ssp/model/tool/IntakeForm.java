package org.studentsuccessplan.ssp.model.tool;

import org.studentsuccessplan.ssp.model.Person;

/**
 * The model for the Intake Form tool.
 * 
 * Currently only a simple wrapper around a fully-normalized {@link Person}.
 */
public class IntakeForm {

	/**
	 * Person with the full tree of data, down to only using identifiers
	 * (non-full objects) when a circular dependency (usually a reference back
	 * to a Person instance) or reference data (system-level lookups like
	 * Challenges, etc.).
	 */
	private Person person;

	/**
	 * Gets the full Person instance.
	 * 
	 * @return the full Person instance
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the full Person instance.
	 * 
	 * @param person
	 *            Person instance
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
}
